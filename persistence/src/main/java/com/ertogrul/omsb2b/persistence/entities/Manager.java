package com.ertogrul.omsb2b.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "DASHBOARD_MANAGERS")
@Entity
public class Manager extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "MANAGER_ID")
    private Long id;


    @Column(name = "USERNAME",unique = true)
    private String username;


    @Column(name = "EMAIL",unique = true)
    private String email;


    @Column(name = "FIRST_NAME")
    private String firstname;


    @Column(name = "LAST_NAME")
    private String lastname;


    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AVATAR")
    private String avatar;


    @Lob
    @Column(name = "LAST_JWT_TOKEN") //for Mysql columnDefinition="TEXT" is not portable instead it is better than
    private String lastJwtToken;


    @Column(name = "IS_ACTIVE",nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active=true;


    @ManyToOne
    @JoinColumn(name = "DST_ID")
    private Distributor distributor;


    @ManyToOne
    @JoinColumn(name = "MFC_POINT_ID")
    private ManufacturePoint manufacturePoint;


    @Column(name = "IS_SUPER_ADMIN",nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean superAdmin=false;


   /* @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "DASHBOARD_MANAGER_ROLES_JOIN",
            joinColumns = @JoinColumn(
                    name = "MANAGER_ID", referencedColumnName = "MANAGER_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ROLE_ID")) *///as in this application we only need 3 roles MANUFACTURER,DISTRIBUTOR,MANAGER we will have One to One relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID",referencedColumnName = "ROLE_ID")
    private ManagerRole role;




}
