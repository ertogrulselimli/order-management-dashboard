package com.ertogrul.omsb2b.persistence.repositories;

import com.ertogrul.omsb2b.persistence.entities.ManufacturePoint;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ManufacturePointRepository extends CustomRepository<ManufacturePoint,Long> {


    Optional<ManufacturePoint> findByName(final String name);

}
