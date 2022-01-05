package com.ertogrul.omsb2b.persistence.repositories;

import com.ertogrul.omsb2b.persistence.entities.Manager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends CustomRepository<Manager,Long> {

    Optional<Manager> findByUsername(final  String username);

    Optional<Manager> findByEmail(final String email);
}
