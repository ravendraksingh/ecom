package io.rks.apigateway.auth;

import io.rks.apigateway.models.AuthResult;
import io.rks.apigateway.models.BaseApiRequest;

public interface Authenticator {
    AuthResult authenticate(BaseApiRequest var1);
}
