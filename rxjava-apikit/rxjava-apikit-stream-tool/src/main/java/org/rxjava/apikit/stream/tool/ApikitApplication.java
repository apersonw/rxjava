package org.rxjava.apikit.stream.tool;

import lombok.Builder;
import lombok.Data;
import org.rxjava.apikit.stream.tool.build.ApidocBuild;
import org.rxjava.apikit.stream.tool.info.ControllerInfo;
import org.rxjava.apikit.stream.tool.scan.ApikitScan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * api工具应用
 */
@Data
@Builder(toBuilder = true)
public class ApikitApplication {

    public Mono<String> start() {
        return new ApikitScan().scan().collectList()
                .flatMap(controllerInfos -> new ApidocBuild().build()
                        .subscriberContext(context -> context.put("controllerInfos",controllerInfos))
                ).subscriberContext(context -> {
                    System.out.println(context);
                    return context;
                });
    }
}
