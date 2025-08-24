package ru.kulbaka.bushunter.service;

import ru.kulbaka.bushunter.dto.carrier.CarrierRequest;
import ru.kulbaka.bushunter.dto.carrier.CarrierResponse;
import ru.kulbaka.bushunter.dto.route.RouteResponse;

import java.util.List;

public interface CarrierService {
    List<CarrierResponse> getAllCarriers();
    CarrierResponse getCarrierById(Long id);
    CarrierResponse createCarrier(CarrierRequest request);
    CarrierResponse updateCarrier(Long id, CarrierRequest request);
    CarrierResponse deleteCarrier(Long id);
}
