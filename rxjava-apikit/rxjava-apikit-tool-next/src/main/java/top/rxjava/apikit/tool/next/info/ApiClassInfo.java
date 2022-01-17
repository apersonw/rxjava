package top.rxjava.apikit.tool.next.info;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author happy
 * Api类信息
 */
public class ApiClassInfo extends CommonClassInfo {
    /**
     * api方法列表
     */
    @Getter
    private final List<ApiMethodInfo> apiMethodList = new ArrayList<>();

    /**
     * 添加api方法信息
     */
    public void addApiMethod(ApiMethodInfo apiMethodInfo) {
        apiMethodInfo.setIndex(apiMethodList.size());
        apiMethodList.add(apiMethodInfo);
    }
}
