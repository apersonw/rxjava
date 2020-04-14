package org.rxjava.api.example.test;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.rxjava.api.example.form.TestForm;
import org.rxjava.api.example.form.TestMultForm;
import org.rxjava.api.example.model.TestModel;
import org.rxjava.api.example.person.TestApi;
import org.rxjava.apikit.client.ClientAdapter;
import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PersonTest {

    @Test
    public void testApi(){
        ReactiveHttpClientAdapter clientAdapter = ReactiveHttpClientAdapter.build(new DefaultFormattingConversionService(), WebClient.builder(), "localhost");
        clientAdapter.init();

        TestApi testApi = new TestApi(clientAdapter);
        TestModel block = testApi.testPath(new ObjectId().toHexString(), new TestForm(), new TestMultForm()).block();
        System.out.println(block);
    }
}
