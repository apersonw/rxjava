package org.rxjava.webflux.gateway.starter.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CustomDiscoveryClientRouteDefinitionLocator extends DiscoveryClientRouteDefinitionLocator implements RouteDefinitionLocator {

    private static final Log log = LogFactory
            .getLog(DiscoveryClientRouteDefinitionLocator.class);

    private final DiscoveryLocatorProperties properties;

    private final String routeIdPrefix;

    private final SimpleEvaluationContext evalCtxt;

    private Flux<List<ServiceInstance>> serviceInstances;

    public CustomDiscoveryClientRouteDefinitionLocator(ReactiveDiscoveryClient discoveryClient,
                                                       DiscoveryLocatorProperties properties) {
        this(discoveryClient, discoveryClient.getClass().getSimpleName(), properties);
        serviceInstances = discoveryClient.getServices()
                .flatMap(service -> discoveryClient.getInstances(service).collectList());
    }

    private CustomDiscoveryClientRouteDefinitionLocator(ReactiveDiscoveryClient discoveryClient, String discoveryClientName,
                                                        DiscoveryLocatorProperties properties) {
        super(discoveryClient, properties);
        this.properties = properties;
        if (StringUtils.hasText(properties.getRouteIdPrefix())) {
            routeIdPrefix = properties.getRouteIdPrefix();
        } else {
            routeIdPrefix = discoveryClientName + "_";
        }
        evalCtxt = SimpleEvaluationContext.forReadOnlyDataBinding().withInstanceMethods()
                .build();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {

        SpelExpressionParser parser = new SpelExpressionParser();
        Expression includeExpr = parser
                .parseExpression(properties.getIncludeExpression());
        Expression urlExpr = parser.parseExpression(properties.getUrlExpression());

        Predicate<ServiceInstance> includePredicate;
        if (properties.getIncludeExpression() == null
                || "true".equalsIgnoreCase(properties.getIncludeExpression())) {
            includePredicate = instance -> true;
        } else {
            includePredicate = instance -> {
                Boolean include = includeExpr.getValue(evalCtxt, instance, Boolean.class);
                if (include == null) {
                    return false;
                }
                return include;
            };
        }

        return serviceInstances.filter(instances -> !instances.isEmpty())
                .map(instances -> instances.get(0)).filter(includePredicate)
                .map(instance -> {
                    String serviceId = instance.getServiceId();

                    RouteDefinition routeDefinition = new RouteDefinition();
                    routeDefinition.setId(this.routeIdPrefix + serviceId);
                    String uri = urlExpr.getValue(evalCtxt, instance, String.class);
                    routeDefinition.setUri(URI.create(uri));

                    final ServiceInstance instanceForEval = new CustomDiscoveryClientRouteDefinitionLocator.DelegatingServiceInstance(
                            instance, properties);

                    for (PredicateDefinition original : this.properties.getPredicates()) {
                        PredicateDefinition predicate = new PredicateDefinition();
                        predicate.setName(original.getName());
                        for (Map.Entry<String, String> entry : original.getArgs()
                                .entrySet()) {
                            String value = getValueFromExpr(evalCtxt, parser,
                                    instanceForEval, entry);
                            predicate.addArg(entry.getKey(), value.replace("rxjava-service-", ""));
                        }
                        routeDefinition.getPredicates().add(predicate);
                    }

                    for (FilterDefinition original : this.properties.getFilters()) {
                        FilterDefinition filter = new FilterDefinition();
                        filter.setName(original.getName());
                        for (Map.Entry<String, String> entry : original.getArgs()
                                .entrySet()) {
                            String value = getValueFromExpr(evalCtxt, parser,
                                    instanceForEval, entry);
                            filter.addArg(entry.getKey(), value.replace("rxjava-service-", ""));
                        }
                        routeDefinition.getFilters().add(filter);
                    }

                    return routeDefinition;
                });
    }

    private String getValueFromExpr(SimpleEvaluationContext evalCtxt, SpelExpressionParser parser,
                                    ServiceInstance instance, Map.Entry<String, String> entry) {
        try {
            Expression valueExpr = parser.parseExpression(entry.getValue());
            return valueExpr.getValue(evalCtxt, instance, String.class);
        } catch (ParseException | EvaluationException e) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to parse " + entry.getValue(), e);
            }
            throw e;
        }
    }

    private static class DelegatingServiceInstance implements ServiceInstance {

        final ServiceInstance delegate;

        private final DiscoveryLocatorProperties properties;

        private DelegatingServiceInstance(ServiceInstance delegate,
                                          DiscoveryLocatorProperties properties) {
            this.delegate = delegate;
            this.properties = properties;
        }

        @Override
        public String getServiceId() {
            if (properties.isLowerCaseServiceId()) {
                return delegate.getServiceId().toLowerCase();
            }
            return delegate.getServiceId();
        }

        @Override
        public String getHost() {
            return delegate.getHost();
        }

        @Override
        public int getPort() {
            return delegate.getPort();
        }

        @Override
        public boolean isSecure() {
            return delegate.isSecure();
        }

        @Override
        public URI getUri() {
            return delegate.getUri();
        }

        @Override
        public Map<String, String> getMetadata() {
            return delegate.getMetadata();
        }

        @Override
        public String getScheme() {
            return delegate.getScheme();
        }

        @Override
        public String toString() {
            return new ToStringCreator(this).append("delegate", delegate)
                    .append("properties", properties).toString();
        }

    }
}
