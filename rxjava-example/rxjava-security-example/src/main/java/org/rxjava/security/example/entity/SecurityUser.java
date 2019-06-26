package org.rxjava.security.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author happy 2019-06-23 15:35
 * Spring需要的UserDetails接口对象
 */
@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    public SecurityUser(String userAuthId, String userId, String identifier, String identityType, List<Role> roles) {
        this.userAuthId=userAuthId;
        this.userId = userId;
        this.identifier = identifier;
        this.identityType = identityType;
        this.roles = roles;
    }

    /**
     * 用户授权Id
     */
    private String userAuthId;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 获取账号拥有的角色
     */
    private List<Role> roles;
    /**
     * 登录类型（手机号,邮箱,用户名）或第三方应用名称（微信 微博等）
     */
    private String identityType;
    /**
     * 标识（手机号,邮箱,用户名或第三方应用的唯一标识）
     */
    private String identifier;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils
                .commaSeparatedStringToAuthorityList(getRoles()
                        .stream()
                        .map(rn -> "ROLE_" + rn.getEnglishName())
                        .collect(Collectors.joining(","))
                );
    }

    /**
     * 此处保存的授权类型
     */
    @Override
    public String getPassword() {
        return getIdentityType();
    }

    @Override
    public String getUsername() {
        return getIdentifier();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
