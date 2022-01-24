package top.rxjava.starter.webflux.api;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import top.rxjava.apikit.client.ClientAdapter;
import top.rxjava.apikit.client.InputParam;
import top.rxjava.common.utils.JavaTimeModuleUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 响应式Http客户端适配器
 *
 * @author wugang
 */
@Data
public class ReactiveHttpClientAdapter implements ClientAdapter {
    private static final Logger log = LogManager.getLogger();
    private WebClient webClient;
    private WebClient.Builder webClientBuilder;
    private String serviceId;
    private String host;
    private String port;
    /**
     * 类型转换函数
     */
    private Function<Object, String> typeConvert;

    @PostConstruct
    public void init() {
        port = ":" + port;

        if (!ObjectUtils.isEmpty(host)) {
            webClient = webClientBuilder.baseUrl("http://" + host + port + "/" + serviceId + "/").build();
            return;
        }

        webClient = webClientBuilder.baseUrl("http://" + serviceId + port + "/").build();
    }

    /**
     * k8s通过serviceId访问,http://serviceId:port/
     */
    private ReactiveHttpClientAdapter(WebClient.Builder webClientBuilder, String serviceId) {
        this.serviceId = serviceId;
        this.webClientBuilder = webClientBuilder;
        this.port = "8080";
    }

    /**
     * k8s通过serviceId访问,http://serviceId:port/
     */
    private ReactiveHttpClientAdapter(WebClient.Builder webClientBuilder, String serviceId, String port) {
        this.webClientBuilder = webClientBuilder;
        this.port = port;
        this.serviceId = serviceId;
        if (ObjectUtils.isEmpty(port)) {
            this.port = "8080";
        }
    }

    /**
     * 通过主机端口访问,http://host:port/serviceId/
     */
    private ReactiveHttpClientAdapter(WebClient.Builder webClientBuilder, String host, String port, String serviceId) {
        this.host = host;
        this.port = port;
        this.serviceId = serviceId;
        this.webClientBuilder = webClientBuilder;
    }

    /**
     * 构建HttpClient适配器
     *
     * @param conversionService 安全的类型转换服务
     * @param webClientBuilder  构建WebClient的Builder
     * @param serviceId         注册中心的ServiceId
     * @return ReactiveHttpClientAdapter
     */
    public static ReactiveHttpClientAdapter build(ConversionService conversionService, WebClient.Builder webClientBuilder, String serviceId) {
        return build(conversionService, webClientBuilder, serviceId, "8080");
    }

    /**
     * 构建HttpClient适配器
     *
     * @param conversionService 安全的类型转换服务
     * @param webClientBuilder  构建WebClient的Builder
     * @param serviceId         注册中心的ServiceId
     * @return ReactiveHttpClientAdapter
     */
    public static ReactiveHttpClientAdapter build(ConversionService conversionService, WebClient.Builder webClientBuilder, String serviceId, String port) {
        ReactiveHttpClientAdapter adapter = new ReactiveHttpClientAdapter(webClientBuilder, serviceId, port);

        adapter.typeConvert = o -> conversionService.convert(o, String.class);
        return adapter;
    }

    /**
     * 构建HttpClient适配器
     *
     * @param conversionService 安全的类型转换服务
     * @param webClientBuilder  构建WebClient的Builder
     * @param serviceId         注册中心的ServiceId
     * @return ReactiveHttpClientAdapter
     */
    public static ReactiveHttpClientAdapter build(ConversionService conversionService, WebClient.Builder webClientBuilder, String host, String port, String serviceId) {
        ReactiveHttpClientAdapter adapter = new ReactiveHttpClientAdapter(webClientBuilder, host, port, serviceId);

        adapter.typeConvert = o -> conversionService.convert(o, String.class);

        return adapter;
    }

    /**
     * 发起http请求
     *
     * @param uri        请求的uri地址
     * @param inputParam 入参
     * @param returnType 返回值类型
     */
    @Override
    public <T> Mono<T> request(String method, String uri, InputParam inputParam, Type returnType) {

        ParameterizedTypeReference<T> typeRef = new ParameterizedTypeReference<>() {
            @Override
            public Type getType() {
                return returnType;
            }
        };

        HttpMethod httpMethod = HttpMethod.valueOf(method);

        List<Map.Entry<String, Object>> urlParams = inputParam.getParamList();
        Object jsonBody = inputParam.getJsonBody();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (urlParams != null) {
            for (Map.Entry<String, Object> item : urlParams) {
                if (item.getValue() instanceof LocalDateTime) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_TIME_FORMAT());
                    params.add(item.getKey(), dateTimeFormatter.format((LocalDateTime) item.getValue()));
                } else {
                    params.add(item.getKey(), typeConvert.apply(item.getValue()));
                }
            }
        }

        //设置post请求参数
        WebClient.RequestBodySpec bodySpec = webClient
                .method(httpMethod)
                .uri(uriBuilder -> uriBuilder.path(uri).queryParams(params).build());

        //请求头设置接收中文语言
        bodySpec.header("Accept-Language", "zh_CN");

        if (StringUtils.isNotBlank(inputParam.getAuthorization())) {
            bodySpec = bodySpec.header(HttpHeaders.AUTHORIZATION, inputParam.getAuthorization());
        }

        WebClient.ResponseSpec retrieve = ObjectUtils.isEmpty(jsonBody) ?
                bodySpec.retrieve() : bodySpec.bodyValue(jsonBody).retrieve();

        return retrieve
                .bodyToMono(typeRef);
    }
}
