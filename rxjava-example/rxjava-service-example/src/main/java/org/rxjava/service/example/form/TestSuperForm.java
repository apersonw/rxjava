package org.rxjava.service.example.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * @author happy 2019-03-27 19:49
 */
@Data
public class TestSuperForm {
    @Min(0)
    private int page = 0;
    @Range(min = 0, max = 20)
    private int pageSize = 10;
}
