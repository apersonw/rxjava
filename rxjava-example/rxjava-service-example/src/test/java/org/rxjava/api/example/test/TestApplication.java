package org.rxjava.api.example.test;

import org.junit.jupiter.api.Test;
import org.rxjava.service.example.RxServiceExampleApplication;
import org.rxjava.service.example.form.TestForm;
import org.rxjava.service.example.person.TestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,classes = RxServiceExampleApplication.class)
public class TestApplication {
    @Autowired
    private TestController testController;
    @Test
    public void testSave(){
        testController.testPath("haha", new TestForm()).block();
    }
}
