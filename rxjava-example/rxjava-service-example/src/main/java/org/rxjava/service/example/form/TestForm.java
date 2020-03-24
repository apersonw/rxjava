package org.rxjava.service.example.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.rxjava.service.example.type.TestEnumType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author happy 2019-01-27 12:16
 */
@Data
public class TestForm extends TestSuperForm {
    @NotNull
    @NotEmpty
    private String id;
    private ObjectId objectId;
    private TestEnumType testEnumType;
}
