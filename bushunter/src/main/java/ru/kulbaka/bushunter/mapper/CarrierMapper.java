package ru.kulbaka.bushunter.mapper;

import ru.kulbaka.bushunter.dto.carrier.CarrierRequest;
import ru.kulbaka.bushunter.dto.carrier.CarrierResponse;
import ru.kulbaka.bushunter.model.Carrier;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CarrierMapper {
    Carrier toModel(CarrierRequest request);

    CarrierResponse toResponse(Carrier carrier);

    Carrier mapRow(ResultSet resultSet, int rowNum) throws SQLException;
}
