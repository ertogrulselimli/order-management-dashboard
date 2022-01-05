package com.ertogrul.omsb2b.service.dtos.product;

import com.ertogrul.omsb2b.service.dtos.measureunit.MeasurementUnitDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Ertogrul Selimli on 10/14/2021
 * @project IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductRawMaterialDto {

    private Long materialId;

    private String materialName;

    private BigDecimal materialQuantity;

    private MeasurementUnitDto measurementUnit;

}
