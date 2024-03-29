package org.rxjava.api.example.test;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.rxjava.api.example.form.TestForm;
import org.rxjava.api.example.model.TestModel;
import org.rxjava.api.example.person.TestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {TestContext.class})
public class TestApplication {
    @Autowired
    private TestApi testApi;

    @Test
    public void testSave() {
        TestForm form = new TestForm();
        form.setObjectId(new ObjectId());
        TestModel haha = testApi.testPath("haha", form).block();
        System.out.println(haha);
    }
}
