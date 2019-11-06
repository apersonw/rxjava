package org.rxjava.apikit.plugin.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author happy
 */
@Data
public class JavaScriptClientTask extends AbstractTask {
    private String nameMaperSource;
    private String nameMaperDist;
    private String outRootPackage;
    private String serviceId;
}
