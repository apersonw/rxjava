package org.rxjava.third.weixin.open.bean.result;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 */
@Data
public class WxOpenAuthorizerListResult {
    private int totalCount;
    private List<Map<String, String>> list;
}
