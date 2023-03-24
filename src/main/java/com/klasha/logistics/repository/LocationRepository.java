package com.klasha.logistics.repository;

import com.klasha.logistics.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, String> {

    Optional<Location> findLocationByCountryCode(String code);

    List<Location> findAll();
}
