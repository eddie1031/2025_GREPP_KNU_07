package io.eddie.accountsservice.repository;

import io.eddie.accountsservice.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);
    Optional<Account> findByCode(String code);
    Optional<Account> findByUsername(String username);

}
