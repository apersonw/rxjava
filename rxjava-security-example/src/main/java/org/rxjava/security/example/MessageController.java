package org.rxjava.security.example;

import org.rxjava.common.core.annotation.Login;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-11 01:05
 */
@RestController
public class MessageController {

    private final HelloWorldMessageService messages;

    public MessageController(HelloWorldMessageService messages) {
        this.messages = messages;
    }

    @Login(false)
    @GetMapping("/message")
    public Mono<String> message() {
        return this.messages.findMessage();
    }
}
