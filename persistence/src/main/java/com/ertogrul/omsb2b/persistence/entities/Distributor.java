package com.ertogrul.omsb2b.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "DISTRIBUTOR")
@Entity
public class Distributor  extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "DISTRIBUTOR_ID")
    private Long id;

    @Column(name = "DST_NAME")
    private String name;

    @Column(name = "REGION")
    private String region;

    @Column(name = "DST_DESCRIPTION")
    private String description;


}
