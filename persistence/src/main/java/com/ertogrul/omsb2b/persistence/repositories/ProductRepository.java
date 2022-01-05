package com.ertogrul.omsb2b.persistence.repositories;


import com.ertogrul.omsb2b.persistence.entities.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CustomRepository<Product,Long> {
}
