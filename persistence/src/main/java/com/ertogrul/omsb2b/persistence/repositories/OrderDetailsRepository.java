package com.ertogrul.omsb2b.persistence.repositories;

import com.ertogrul.omsb2b.persistence.entities.OrderDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository  extends CustomRepository<OrderDetails,Long> {
}
