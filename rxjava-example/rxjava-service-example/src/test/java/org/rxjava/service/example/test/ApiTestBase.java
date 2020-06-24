package org.rxjava.service.example.test;

import org.junit.runner.RunWith;
import org.rxjava.api.example.person.TestApi;
import org.rxjava.apikit.client.ClientAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = TestApiContext.class)
public class ApiTestBase {
    @Autowired
    protected TestApi testApi;
}
