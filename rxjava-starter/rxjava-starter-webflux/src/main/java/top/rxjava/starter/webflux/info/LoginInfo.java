package top.rxjava.starter.webflux.info;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * 用户信息
 * @author happy
 */
@Data
public class LoginInfo {
    /**
     * 用户Id
     */
    private ObjectId userId;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 租户Id
     */
    private ObjectId tenantId;
}