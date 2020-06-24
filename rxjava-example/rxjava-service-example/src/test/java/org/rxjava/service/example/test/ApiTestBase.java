package org.rxjava.service.example.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.rxjava.api.example.person.TestApi;
import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = ApiContext.class)
public class ApiTestBase {
    @Autowired
    protected TestApi testApi;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 处理登陆并注入token到ReactiveHttpClientAdapter中
     */
    @Before
    public void init(){
        Map<String, ReactiveHttpClientAdapter> clientAdapterMap = applicationContext.getBeansOfType(ReactiveHttpClientAdapter.class);

        clientAdapterMap.forEach((key,value)->{

        });
    }
}
