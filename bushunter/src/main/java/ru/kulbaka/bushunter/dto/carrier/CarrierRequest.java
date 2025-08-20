package ru.kulbaka.bushunter.dto.carrier;

import jakarta.validation.constraints.NotBlank;

public record CarrierRequest(
        @NotBlank String name,
        @NotBlank String phone
) {}
