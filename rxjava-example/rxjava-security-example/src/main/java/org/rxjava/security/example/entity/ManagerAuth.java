package org.rxjava.security.example.entity;

import lombok.Data;
import org.rxjava.security.example.status.ManagerAuthStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING;

/**
 * @author happy 2019-04-27 00:06
 * 管理员授权信息
 */
@Data
@Document
@CompoundIndexes(
        @CompoundIndex(
                name = "identityType_identifier",
                def = "{'identityType': 1, 'identifier': 1}",
                unique = true
        )
)
public class ManagerAuth {
    @Id
    private String id;
    /**
     * 管理者Id
     */
    private String managerId;
    /**
     * 登录类型（手机号,邮箱,用户名）或第三方应用名称（微信 微博等）
     */
    private String identityType;
    /**
     * 标识（手机号,邮箱,用户名或第三方应用的唯一标识）
     */
    private String identifier;
    /**
     * 密码凭证（站内的保存密码，站外的不保存或保存token）
     */
    private String credential;
    /**
     * 状态
     */
    private String status = ManagerAuthStatus.INIT.name();
    /**
     * 创建日期
     */
    @CreatedDate
    @Indexed(direction = DESCENDING)
    private LocalDateTime createDate;
    /**
     * 更新日期
     */
    @LastModifiedDate
    private LocalDateTime updateDate;
}
