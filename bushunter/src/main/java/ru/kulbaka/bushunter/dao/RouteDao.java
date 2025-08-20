package ru.kulbaka.bushunter.dao;

import ru.kulbaka.bushunter.model.Route;

import java.util.List;
import java.util.Optional;

public interface RouteDao {
    List<Route> findAll();
    Optional<Route> findById(Long id);
    Long create(Route route);
    void update(Route route);
    void delete(Long id);
    boolean existsById(Long id);
}
