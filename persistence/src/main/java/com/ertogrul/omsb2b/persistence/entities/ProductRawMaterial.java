package com.ertogrul.omsb2b.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Ertogrul Selimli on 10/12/2021
 * @project IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_RAW_MATERIAL")
public class ProductRawMaterial {

    @Id
    @Column(name = "PRODUCT_MATERIAL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID",referencedColumnName = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "MATERIAL_ID",referencedColumnName = "MATERIAL_ID")
    private RawMaterial rawMaterial;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID",referencedColumnName = "ID")
    private MeasurementUnit unit;

}
