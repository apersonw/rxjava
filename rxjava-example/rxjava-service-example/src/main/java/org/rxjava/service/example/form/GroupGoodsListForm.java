package org.rxjava.service.example.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class GroupGoodsListForm extends PageForm {
    @NotBlank
    private String groupId;
}
