// package org.rxjava.service.example.person;
//
// import lombok.extern.slf4j.Slf4j;
// import org.rxjava.api.person.example.TestApi;
// import org.rxjava.api.person.example.form.TestBodyForm;
// import org.rxjava.api.person.example.form.TestForm;
// import org.rxjava.api.person.example.form.TestMultForm;
// import org.rxjava.api.person.example.model.TestModel;
// import org.rxjava.apikit.client.ClientAdapter;
// import org.rxjava.common.core.annotation.Login;
// import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.format.support.DefaultFormattingConversionService;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.reactive.function.client.WebClient;
// import reactor.core.publisher.Mono;
//
// @RestController
// @Slf4j
// public class TestApiController {
//     @Bean
//     public ClientAdapter clientAdapter() {
//         return ReactiveHttpClientAdapter.build(new DefaultFormattingConversionService(), WebClient.builder(), "127.0.0.1", "8080", "");
//     }
//
//     @Bean
//     public TestApi testApi(ClientAdapter clientAdapter) {
//         return new TestApi(clientAdapter);
//     }
//
//     @Autowired
//     private TestApi testApi;
//
//     @Login(false)
//     @GetMapping("testApi")
//     public Mono<TestModel> testApi() {
//         return testApi.testPath(
//                 "iiai",
//                 "asdfsafsdf",
//                 new TestBodyForm(),
//                 new TestForm(),
//                 new TestMultForm())
//                 .map(a -> {
//                     System.out.println(a);
//                     return a;
//                 });
//     }
// }
