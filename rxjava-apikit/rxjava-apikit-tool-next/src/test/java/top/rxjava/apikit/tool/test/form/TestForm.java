package top.rxjava.apikit.tool.test.form;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class TestForm {
    private ObjectId id;
    private String testId;
}
