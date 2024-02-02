package org.wsd.app.security.auth.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignInResponse {
    private String accessToken;
}
