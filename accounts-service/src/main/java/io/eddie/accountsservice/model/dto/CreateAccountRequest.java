package io.eddie.accountsservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequest(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank
        String email
) {
}
