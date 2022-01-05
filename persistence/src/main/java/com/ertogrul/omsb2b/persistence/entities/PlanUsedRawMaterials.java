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
@Table(name = "PLAN_USED_RAW_MATERIALS")
public class PlanUsedRawMaterials extends AbstractAuditingEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PLAN_ID",referencedColumnName = "PLAN_ID")
    private ManufacturePlan plan;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_MATERIAL_ID")
    private ProductRawMaterial rawMaterial;

    @Column(name = "EXPECTED_QUANTITY")
    private BigDecimal expectedQuantity;

    @Column(name = "ACTUAL_QUANTITY")
    private BigDecimal actualQuantity;

    @Column(name = "UNIT")
    private MeasurementUnit unit;

}
