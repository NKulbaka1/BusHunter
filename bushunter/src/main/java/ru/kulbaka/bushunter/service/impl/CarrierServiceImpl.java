package ru.kulbaka.bushunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kulbaka.bushunter.dao.CarrierDao;
import ru.kulbaka.bushunter.dto.carrier.CarrierRequest;
import ru.kulbaka.bushunter.dto.carrier.CarrierResponse;
import ru.kulbaka.bushunter.exception.EntityNotFoundException;
import ru.kulbaka.bushunter.mapper.CarrierMapper;
import ru.kulbaka.bushunter.model.Carrier;
import ru.kulbaka.bushunter.service.CarrierService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarrierServiceImpl implements CarrierService {
    private final CarrierDao carrierDao;
    private final CarrierMapper carrierMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CarrierResponse> getAllCarriers() {
        return carrierDao.findAll().stream()
                .map(carrierMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CarrierResponse getCarrierById(Long id) {
        Carrier carrier = carrierDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Перевозчик с айди " + id + " не найден"));
        return carrierMapper.toResponse(carrier);
    }

    @Override
    @Transactional
    public CarrierResponse createCarrier(CarrierRequest request) {
        Carrier carrier = carrierMapper.toModel(request);
        Long id = carrierDao.create(carrier);

        Carrier savedCarrier = getCarrierModelById(id);
        return carrierMapper.toResponse(savedCarrier);
    }

    @Override
    @Transactional
    public CarrierResponse updateCarrier(Long id, CarrierRequest request) {
        if (!carrierDao.existsById(id)) {
            throw new EntityNotFoundException("Перевозчик с айди " + id + " не найден");
        }

        Carrier carrier = carrierMapper.toModel(request);
        carrier.setId(id);
        carrierDao.update(carrier);

        return getCarrierById(id);
    }

    @Override
    @Transactional
    public CarrierResponse deleteCarrier(Long id) {
        Carrier carrier = getCarrierModelById(id);

        carrierDao.delete(id);

        return carrierMapper.toResponse(carrier);
    }

    private Carrier getCarrierModelById(Long id) {
        return carrierDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Перевозчик с айди " + id + " не найден"));
    }
}
