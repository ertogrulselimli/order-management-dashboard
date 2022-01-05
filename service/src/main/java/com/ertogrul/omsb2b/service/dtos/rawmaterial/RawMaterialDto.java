package com.ertogrul.omsb2b.service.dtos.rawmaterial;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;

/**
 * @author Ertogrul Selimli on 10/13/2021
 * @project IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
public class RawMaterialDto {

    private Long id;

    private String name;

    private String description;

    private boolean forPalet;

}
