package org.rxjava.security.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author happy 2019-06-11 23:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequest {
    private String username;
    private String password;
}