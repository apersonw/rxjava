package org.rxjava.service.example.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author happy 2019-06-30 00:51
 */
@Data
public class CheckPermissionForm {
    @NotEmpty
    String userAuthId;
    @NotEmpty
    String path;
    @NotEmpty
    String method;
}
