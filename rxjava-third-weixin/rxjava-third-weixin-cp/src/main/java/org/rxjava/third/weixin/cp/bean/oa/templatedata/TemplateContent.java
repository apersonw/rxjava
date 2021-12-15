package top.rxjava.third.weixin.cp.bean.oa.templatedata;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class TemplateContent implements Serializable {

    private static final long serialVersionUID = -5640250983775840865L;

    private List<TemplateControls> controls;
}
