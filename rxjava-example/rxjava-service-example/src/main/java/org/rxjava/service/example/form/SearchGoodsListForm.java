package org.rxjava.service.example.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 商品列表搜索文本
 */
@Setter
@Getter
public class SearchGoodsListForm extends PageForm {
    /**
     * 搜索文本
     */
    @NotBlank
    private String searchText;
}
