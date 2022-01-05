package com.ertogrul.omsb2b.service.dtos.product;

import com.ertogrul.omsb2b.persistence.enums.WarrantyPeriodTerm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ListProduct {

    private Long id;

    private String name;

    private String description;

    private Integer warrantyPeriod;

    private WarrantyPeriodTerm warrantyPeriodTerm;

    private String barCode;
}
