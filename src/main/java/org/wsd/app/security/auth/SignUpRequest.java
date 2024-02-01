package org.wsd.app.security.auth;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignUpRequest {
    private String username;
    private String password;
}
