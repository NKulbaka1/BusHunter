package ru.kulbaka.bushunter.dao;

import ru.kulbaka.bushunter.model.Carrier;

import java.util.List;
import java.util.Optional;

public interface CarrierDao {
    List<Carrier> findAll();
    Optional<Carrier> findById(Long id);
    Long create(Carrier carrier);
    void update(Carrier carrier);
    void delete(Long id);
    boolean existsById(Long id);
}
