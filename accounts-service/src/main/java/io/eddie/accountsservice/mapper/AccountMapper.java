package io.eddie.accountsservice.mapper;

import io.eddie.accountsservice.model.dto.AccountDescription;
import io.eddie.accountsservice.model.dto.AccountDetails;
import io.eddie.accountsservice.model.entity.Account;

public class AccountMapper {

    public static AccountDetails toDetail(Account account) {
        return new AccountDetails(
                account.getCode(),
                account.getUsername(),
                account.getPassword(),
                account.getRoles()
        );
    }

    public static AccountDescription toDescription(Account account) {
        return new AccountDescription(
                account.getCode(),
                account.getUsername()
        );
    }

}
