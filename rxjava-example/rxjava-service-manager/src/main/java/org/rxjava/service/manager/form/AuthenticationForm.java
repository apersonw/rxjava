package org.rxjava.service.manager.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthenticationForm {
    /**
     * Token
     */
    @NotEmpty
    private String token;
    /**
     * 请求路径
     */
    @NotEmpty
    private String path;
    /**
     * 请求方法：GET，POST，PUT，PATCH，DELETE等
     */
    @NotEmpty
    private String method;
}
