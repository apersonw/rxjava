package org.rxjava.service.example.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author happy 2019-01-27 12:16
 */
@Getter
@Setter
public class TestForm extends PageForm {
    @NotNull
    @NotEmpty
    private String formId;
}
