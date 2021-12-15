package top.rxjava.third.weixin.cp.bean.oa.templatedata.control;

import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class TemplateDate implements Serializable {

    private static final long serialVersionUID = 1300634733160349684L;
    /**
     * day-日期；hour-日期+时间
     */
    private String type;
}
