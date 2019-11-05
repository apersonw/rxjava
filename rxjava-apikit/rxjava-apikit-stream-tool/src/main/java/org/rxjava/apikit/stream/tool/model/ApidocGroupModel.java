package org.rxjava.apikit.stream.tool.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * apidoc 分组
 */
@Getter
@Setter
public class ApidocGroupModel {
    /**
     * 分组名
     */
    private String groupName;
    /**
     * api文档列表
     */
    private List<ApidocModel> apidocModels;
}
