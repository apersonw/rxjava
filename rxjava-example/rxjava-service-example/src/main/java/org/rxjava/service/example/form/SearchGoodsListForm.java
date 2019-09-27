package org.rxjava.service.example.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品列表搜索文本
 */
@Data
public class SearchGoodsListForm extends PageForm {
    /**
     * 搜索文本
     */
    @NotBlank
    private String searchText;
}
