package com.ertogrul.omsb2b.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "ORDER_DETAILS")
@Entity
public class OrderDetails extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ORDER_DETAIL_ID")
    private Long id;






}
