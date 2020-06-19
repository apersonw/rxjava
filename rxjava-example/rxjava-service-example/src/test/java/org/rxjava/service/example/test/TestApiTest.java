package org.rxjava.service.example.test;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rxjava.api.example.form.TestBodyForm;
import org.rxjava.api.example.form.TestForm;
import org.rxjava.api.example.form.TestMultForm;
import org.rxjava.api.example.model.TestModel;
import org.rxjava.api.example.person.TestApi;
import org.rxjava.apikit.client.ClientAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = TestApiContext.class)
public class TestApiTest {
    @Autowired
    private ClientAdapter clientAdapter;

    @Test
    public void testLogin() {
        TestApi testApi = new TestApi(clientAdapter);
        Mono<TestModel> testModelMono = testApi.testPath(new ObjectId().toHexString(), "", new TestBodyForm(), new TestForm(), new TestMultForm());
        TestModel block = testModelMono.block();
        System.out.println(block);
    }
}
