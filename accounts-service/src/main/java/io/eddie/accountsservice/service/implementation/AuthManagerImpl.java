package io.eddie.accountsservice.service.implementation;

import io.eddie.accountsservice.service.AccountService;
import io.eddie.accountsservice.service.AuthManager;
import io.eddie.accountsservice.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthManagerImpl implements AuthManager {

    @Value("${custom.jwt.expiration}")
    private Long expiration;

    private final TokenProvider tokenProvider;
    private final AccountService accountService;

    @Override
    public String issueAccessToken(String accountCode) {
        return tokenProvider.issue(expiration, Map.of("accountCode", accountCode));
    }


}
