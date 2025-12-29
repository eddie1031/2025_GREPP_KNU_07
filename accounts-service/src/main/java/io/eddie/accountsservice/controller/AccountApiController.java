package io.eddie.accountsservice.controller;

import io.eddie.accountsservice.mapper.AccountMapper;
import io.eddie.accountsservice.model.dto.AccountDescription;
import io.eddie.accountsservice.model.dto.CreateAccountRequest;
import io.eddie.accountsservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountApiController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountDescription> createAccount(
            @RequestBody @Valid CreateAccountRequest request
            ) {

        return ResponseEntity.ok(
                AccountMapper.toDescription(
                        service.create(request)
                )
        );
    }


}
