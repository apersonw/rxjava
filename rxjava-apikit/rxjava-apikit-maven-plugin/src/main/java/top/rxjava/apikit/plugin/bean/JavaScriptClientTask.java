package top.rxjava.apikit.plugin.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author happy
 */
@Getter
@Setter
public class JavaScriptClientTask extends AbstractTask {
    private String nameMaperSource;
    private String nameMaperDist;
    private String outRootPackage;
    private String serviceId;
}
