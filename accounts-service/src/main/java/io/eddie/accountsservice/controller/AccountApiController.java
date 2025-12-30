package io.eddie.accountsservice.controller;

import io.eddie.accountsservice.mapper.AccountMapper;
import io.eddie.accountsservice.model.dto.AccountDescription;
import io.eddie.accountsservice.model.dto.CreateAccountRequest;
import io.eddie.accountsservice.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @GetMapping("/test")
    public ResponseEntity<String> test(
            HttpServletRequest request
    ) {

        String header = request.getHeader("X-KNU-TOKEN");
        log.info("header : {}", header);

        return ResponseEntity.ok("test");
    }



}
