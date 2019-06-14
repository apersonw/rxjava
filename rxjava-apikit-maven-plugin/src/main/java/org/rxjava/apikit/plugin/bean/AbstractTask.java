package org.rxjava.apikit.plugin.bean;

import lombok.Data;

/**
 * @author happy
 */
@Data
abstract class AbstractTask implements Task {
    private String outPath;
}
