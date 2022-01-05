package com.ertogrul.omsb2b.persistence.repositories;

import com.ertogrul.omsb2b.persistence.entities.ProductRawMaterial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ertogrul Selimli on 10/14/2021
 * @project IntelliJ IDEA
 */
@Repository
public interface ProductRawMaterialRepository extends CustomRepository<ProductRawMaterial,Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM PRODUCT_RAW_MATERIAL u WHERE u.PRODUCT_ID=:product_id")
    List<ProductRawMaterial> findByProductNative(@Param(("product_id")) final Long productId);

}
