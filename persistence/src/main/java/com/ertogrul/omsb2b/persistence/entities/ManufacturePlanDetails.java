package com.ertogrul.omsb2b.persistence.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "MANUFACTURE_PLAN_DETAILS")
@Entity
public class ManufacturePlanDetails extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "PLAN_DETAIL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID",referencedColumnName = "PRODUCT_ID")
    private Product product;

    @Column(name = "TARGETED_QUANTITY")
    private Integer targetedQuantity;

    @Column(name = "ACTUAL_QUANTITY")
    private Integer actualQuantity;

    @ManyToOne
    @JoinColumn(name = "PLAN_ID",referencedColumnName = "PLAN_ID")
    private ManufacturePlan manufacturePlan;



}
