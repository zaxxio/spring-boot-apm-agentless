package org.wsd.app.security.auth.resquest;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignInRequest {
    private String username;
    private String password;
}
