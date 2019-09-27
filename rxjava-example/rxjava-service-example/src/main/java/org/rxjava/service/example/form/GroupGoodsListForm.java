package org.rxjava.service.example.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupGoodsListForm extends PageForm {
    @NotBlank
    private String groupId;
}
