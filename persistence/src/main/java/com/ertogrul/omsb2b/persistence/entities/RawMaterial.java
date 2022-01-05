package com.ertogrul.omsb2b.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author Ertogrul Selimli on 10/12/2021
 * @project IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "RAW_MATERIAL")
public class RawMaterial extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATERIAL_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_FOR_PALET",nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean forPalet;

}
