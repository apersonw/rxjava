package org.rxjava.webflux.core.info;

import org.bson.types.ObjectId;

/**
 * @author happy 2019-04-14 14:00
 * 登陆信息
 */
public interface LoginInfo {
    /**
     * 用户Id
     */
    ObjectId userId = null;
}