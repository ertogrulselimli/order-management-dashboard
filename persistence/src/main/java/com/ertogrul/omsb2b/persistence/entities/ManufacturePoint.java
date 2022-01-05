package com.ertogrul.omsb2b.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "MANUFACTURE_POINTS")
@Entity
public class ManufacturePoint extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "POINT_ID")
    private Long id;

    @Column(name = "POINT_NAME")
    private String name;


    @Column(name = "POINT_DESC")
    private String description;


    @Column(name = "POINT_REGION")
    private String region;


}
