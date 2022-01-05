package com.ertogrul.omsb2b.persistence.repositories;

import com.ertogrul.omsb2b.persistence.entities.Distributor;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorRepository  extends CustomRepository<Distributor,Long> {
}
