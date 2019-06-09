package org.rxjava.service.example.form;

import lombok.Getter;
import lombok.Setter;

/**
 * @author happy 2019-03-27 19:49
 */
@Getter
@Setter
public class PageForm {
    private int page;
    private int pageSize = 10;
}
