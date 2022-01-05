package com.ertogrul.omsb2b.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Ertogrul Selimli on 10/1/2021
 * @project IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@Table(name = "MEASUREMENT_UNIT")
@Entity
public class MeasurementUnit extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",updatable = false,nullable = false)
    private Long id;

    @Column(name = "DESCRIPTION") //kg,litr,gram,and etc
    private String description;

}
