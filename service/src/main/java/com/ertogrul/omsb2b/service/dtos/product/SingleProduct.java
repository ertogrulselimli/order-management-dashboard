package com.ertogrul.omsb2b.service.dtos.product;

import com.ertogrul.omsb2b.persistence.enums.WarrantyPeriodTerm;
import com.ertogrul.omsb2b.service.dtos.measureunit.MeasurementUnitDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ertogrul Selimli on 10/16/2021
 * @project IntelliJ IDEA
 */
@NoArgsConstructor
@Getter
@Setter
public class SingleProduct {

    private String name;

    private String description;

    private Integer warrantyPeriod;

    private WarrantyPeriodTerm warrantyPeriodTerm;

    private String barCode;

    private BigDecimal price;

    private BigDecimal recomRetailPrice; //recommended retail price

    private BigDecimal weight;

    private MeasurementUnitDto weightUnit;

    private List<ProductRawMaterialDto> rawMaterials;
}
