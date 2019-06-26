package org.rxjava.security.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-26 15:07
 */
@RestController
@RequestMapping("/apis")
@Api(value = "Swagger test Controller", description = "learn how to use swagger")
public class SwaggerController {

    @GetMapping
    @ApiOperation(value = "GET Method", response = String.class)
    public Mono<String> get() {
        return Mono.just("this is GET Met" + "hod.");
    }

    @PostMapping
    @ApiOperation(value = "POST Method", response = String.class)
    public Mono<String> post() {
        return Mono.just("this is POST Method.");
    }

    @PutMapping
    @ApiOperation(value = "PUT Method", response = String.class)
    public Mono<String> put() {
        return Mono.just("this is PUT Method.");
    }

    @DeleteMapping
    @ApiOperation(value = "DELETE Method", response = String.class)
    public Mono<String> delete() {
        return Mono.just("this is DELETE Method.");
    }

}