package org.rxjava.third.tencent.weixin.cp.bean.oa.templatedata.control;

import lombok.Data;
import org.rxjava.third.tencent.weixin.cp.bean.oa.templatedata.TemplateVacationItem;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class TemplateVacation implements Serializable {

    private List<TemplateVacationItem> item;

}
