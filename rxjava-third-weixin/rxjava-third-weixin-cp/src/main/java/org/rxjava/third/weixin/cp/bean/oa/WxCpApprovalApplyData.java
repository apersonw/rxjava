package top.rxjava.third.weixin.cp.bean.oa;

import lombok.Data;
import top.rxjava.third.weixin.cp.bean.oa.applydata.Content;

import java.io.Serializable;
import java.util.List;

/**
 * 审批申请数据
 */
@Data
public class WxCpApprovalApplyData implements Serializable {

    private static final long serialVersionUID = 4061352949894274704L;

    private List<Content> contents;

}
