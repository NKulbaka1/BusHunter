package ru.kulbaka.bushunter.mapper.impl;

import org.springframework.stereotype.Component;
import ru.kulbaka.bushunter.dto.carrier.CarrierRequest;
import ru.kulbaka.bushunter.dto.carrier.CarrierResponse;
import ru.kulbaka.bushunter.mapper.CarrierMapper;
import ru.kulbaka.bushunter.model.Carrier;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CarrierMapperImpl implements CarrierMapper {

    @Override
    public Carrier toModel(CarrierRequest request) {
        if (request == null) {
            return null;
        }

        Carrier carrier = new Carrier();
        carrier.setName(request.getName());
        carrier.setPhone(request.getPhone());

        return carrier;
    }

    @Override
    public CarrierResponse toResponse(Carrier carrier) {
        if (carrier == null) return null;

        return new CarrierResponse(
                carrier.getId(),
                carrier.getName(),
                carrier.getPhone()
        );
    }

    @Override
    public Carrier mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Carrier carrier = new Carrier();
        carrier.setId(resultSet.getLong("carrier_id"));
        carrier.setName(resultSet.getString("carrier_name"));
        carrier.setPhone(resultSet.getString("carrier_phone"));
        return carrier;
    }
}