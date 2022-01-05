package com.ertogrul.omsb2b.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "MANUFACTURE_PLAN")
@Entity
public class ManufacturePlan extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "PLAN_ID")
    private Long id;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "POINT_ID")
    private ManufacturePoint manufacturePoint;

}
