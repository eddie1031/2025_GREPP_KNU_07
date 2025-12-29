package io.eddie.accountsservice.service.handler;

import io.eddie.accountsservice.model.dto.AccountDetails;
import io.eddie.accountsservice.service.AuthManager;
import io.eddie.accountsservice.service.implementation.AuthManagerImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl
        implements AuthenticationSuccessHandler {

    private final AuthManager authManager;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        AccountDetails details = (AccountDetails) authentication.getPrincipal();

        String token = authManager.issueAccessToken(details.getAccountCode());

        response.addHeader("X-KNU-TOKEN", token);

    }

}
