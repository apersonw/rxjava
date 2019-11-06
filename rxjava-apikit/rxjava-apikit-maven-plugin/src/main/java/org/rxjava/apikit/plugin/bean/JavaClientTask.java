package org.rxjava.apikit.plugin.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author happy
 */
@Getter
@Setter
public class JavaClientTask extends AbstractTask {
    private String nameMaperSource;
    private String nameMaperDist;
    private String outRootPackage;
}
