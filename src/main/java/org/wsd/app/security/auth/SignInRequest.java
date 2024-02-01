package org.wsd.app.security.auth;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignInRequest {
    private String username;
    private String password;
}
