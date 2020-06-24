package org.rxjava.service.example.test;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.rxjava.api.example.form.TestBodyForm;
import org.rxjava.api.example.form.TestForm;
import org.rxjava.api.example.form.TestMultForm;
import org.rxjava.api.example.model.TestModel;
import reactor.core.publisher.Mono;

public class TestApiTest extends ApiTestBase {
    @Test
    public void testLogin() {
        Mono<TestModel> testModelMono = testApi.testPath(new ObjectId().toHexString(), "", new TestBodyForm(), new TestForm(), new TestMultForm());
        TestModel block = testModelMono.block();
        System.out.println(block);
    }
}
