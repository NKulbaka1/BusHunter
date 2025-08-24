package ru.kulbaka.bushunter.dto.carrier;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;
}