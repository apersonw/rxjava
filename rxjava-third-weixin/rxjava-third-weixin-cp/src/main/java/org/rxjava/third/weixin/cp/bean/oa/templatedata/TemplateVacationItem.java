package org.rxjava.third.weixin.cp.bean.oa.templatedata;

import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class TemplateVacationItem implements Serializable {

    private static final long serialVersionUID = 4510594801023791319L;

    private Integer id;

    private TemplateTitle name;
}
