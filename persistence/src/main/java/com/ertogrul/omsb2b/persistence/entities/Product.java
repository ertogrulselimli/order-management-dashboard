package com.ertogrul.omsb2b.persistence.entities;


import com.ertogrul.omsb2b.persistence.enums.WarrantyPeriodTerm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "PRODUCT")
@Entity
public class Product extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID",updatable = false,nullable = false)
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String description;

    @Column(name = "WARRANTY_PERIOD")
    private Integer warrantyPeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "WARRANTY_PERIOD_TERM")
    private WarrantyPeriodTerm warrantyPeriodTerm;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "RECOMMENDED_RETAIL_PRICE")
    private BigDecimal recomRetailPrice; //recommended retail price

    @Column(name = "WEIGHT")
    private BigDecimal weight;

    @Column(name = "WEIGHT_UNIT")
    private MeasurementUnit weightUnit;


    @Column(name = "BAR_CODE")
    private String barCode;

}
