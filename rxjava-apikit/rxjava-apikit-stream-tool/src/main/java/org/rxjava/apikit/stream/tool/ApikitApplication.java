package org.rxjava.apikit.stream.tool;

import lombok.Builder;
import lombok.Data;
import org.rxjava.apikit.stream.tool.info.ControllerInfo;
import org.rxjava.apikit.stream.tool.scan.ApikitScan;
import reactor.core.publisher.Flux;

/**
 * api工具应用
 */
@Data
@Builder(toBuilder = true)
public class ApikitApplication {

    public Flux<ControllerInfo> start() {
        return new ApikitScan().scan();
    }
}
