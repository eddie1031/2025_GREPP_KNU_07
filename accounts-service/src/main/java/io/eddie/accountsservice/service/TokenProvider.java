package io.eddie.accountsservice.service;

import io.eddie.accountsservice.model.dto.TokenBody;

import java.util.Map;

public interface TokenProvider {

    String issue(Long validTime, Map<String, Object> claims);
    boolean validate(String t);
    TokenBody parse(String t);
    Map<String, Object> getClaims(String t);

}
