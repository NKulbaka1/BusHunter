package ru.kulbaka.bushunter.dto.carrier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierResponse {
    private Long id;
    private String name;
    private String phone;
}