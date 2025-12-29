package io.eddie.accountsservice.service;

public interface AuthManager {

    String issueAccessToken(String accountCode);

}
