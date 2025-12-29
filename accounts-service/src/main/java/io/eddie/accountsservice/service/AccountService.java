package io.eddie.accountsservice.service;

import io.eddie.accountsservice.model.dto.CreateAccountRequest;
import io.eddie.accountsservice.model.entity.Account;

public interface AccountService {

    Account create(CreateAccountRequest request);

}
