package org.rxjava.security.example;

import org.rxjava.security.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-12 00:02
 */
@RestController
public class AuthenticationREST {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PBKDF2Encoder passwordEncoder;

    @Autowired
    private UserService userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
        return userRepository.findByUsername(ar.getUsername()).map((userDetails) -> {
            if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
                return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}