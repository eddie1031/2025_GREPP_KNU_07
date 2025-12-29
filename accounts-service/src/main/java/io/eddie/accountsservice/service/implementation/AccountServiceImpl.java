package io.eddie.accountsservice.service.implementation;

import io.eddie.accountsservice.mapper.AccountMapper;
import io.eddie.accountsservice.model.dto.CreateAccountRequest;
import io.eddie.accountsservice.model.entity.Account;
import io.eddie.accountsservice.repository.AccountJpaRepository;
import io.eddie.accountsservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountJpaRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Account> accountOptional = repository.findByUsername(username);

        Account foundAccount = accountOptional.orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));

        return AccountMapper.toDetail(foundAccount);
    }


    @Override
    @Transactional
    public Account create(CreateAccountRequest request) {

        Account account = Account.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .build();

        account = repository.save(account);

        return account;
    }

}
