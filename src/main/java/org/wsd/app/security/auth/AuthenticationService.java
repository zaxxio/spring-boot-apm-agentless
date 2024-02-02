package org.wsd.app.security.auth;

import org.wsd.app.payload.Payload;
import org.wsd.app.security.auth.response.SignInResponse;
import org.wsd.app.security.auth.response.SignUpResponse;
import org.wsd.app.security.auth.resquest.SignInRequest;
import org.wsd.app.security.auth.resquest.SignUpRequest;

public interface AuthenticationService {
    Payload<SignInResponse> signIn(SignInRequest signInRequest);

    Payload<SignUpResponse> signUp(SignUpRequest signUpRequest);
}
