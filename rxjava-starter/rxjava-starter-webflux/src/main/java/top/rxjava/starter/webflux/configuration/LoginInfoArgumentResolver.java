package top.rxjava.starter.webflux.configuration;import top.rxjava.common.core.info.LoginInfo;import org.springframework.core.MethodParameter;import org.springframework.core.ReactiveAdapterRegistry;import org.springframework.web.reactive.BindingContext;import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolverSupport;import org.springframework.web.reactive.result.method.SyncHandlerMethodArgumentResolver;import org.springframework.web.server.ServerWebExchange;/** * 登陆信息参数解析 * @author happy */public class LoginInfoArgumentResolver extends HandlerMethodArgumentResolverSupport implements SyncHandlerMethodArgumentResolver {    static final String LOGIN_REQUEST_ATTRIBUTE = LoginInfoArgumentResolver.class.getName() + "LOGIN_REQUEST_ATTRIBUTE";    public LoginInfoArgumentResolver(ReactiveAdapterRegistry adapterRegistry) {        super(adapterRegistry);    }    @Override    public boolean supportsParameter(MethodParameter parameter) {        return parameter.getParameterType().equals(LoginInfo.class);    }    @Override    public Object resolveArgumentValue(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {        return exchange.getAttribute(LoginInfoArgumentResolver.LOGIN_REQUEST_ATTRIBUTE);    }}