package org.rxjava.common.core.status;

/**
 * @author happy 2019-04-27 10:31
 * 实体生命周期状态
 */
public enum EntityStatus {
    /**
     * 初始化
     */
    INIT,
    /**
     * 正常
     */
    NORMAL,
    /**
     * 草稿
     */
    DRAFT,
    /**
     * 软删除状态
     */
    DELETE
}
