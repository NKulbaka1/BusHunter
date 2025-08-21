package ru.kulbaka.bushunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kulbaka.bushunter.dto.carrier.CarrierRequest;
import ru.kulbaka.bushunter.dto.carrier.CarrierResponse;
import ru.kulbaka.bushunter.service.CarrierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carriers")
@RequiredArgsConstructor
public class CarrierController {
    private final CarrierService carrierService;

    @GetMapping
    public List<CarrierResponse> getAllCarriers() {
        return carrierService.getAllCarriers();
    }

    @GetMapping("/{id}")
    public CarrierResponse getCarrier(@PathVariable Long id) {
        return carrierService.getCarrierById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarrierResponse createCarrier(@Valid @RequestBody CarrierRequest request) {
        return carrierService.createCarrier(request);
    }

    @PutMapping("/{id}")
    public CarrierResponse updateCarrier(@PathVariable Long id, @Valid @RequestBody CarrierRequest request) {
        return carrierService.updateCarrier(id, request);
    }

    @DeleteMapping("/{id}")
    public CarrierResponse deleteCarrier(@PathVariable Long id) {
        return carrierService.deleteCarrier(id);
    }
}
