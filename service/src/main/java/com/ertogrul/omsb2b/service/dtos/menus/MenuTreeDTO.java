package com.ertogrul.omsb2b.service.dtos.menus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class MenuTreeDTO {

    private Long id;

    private String name;

    private String path;

    private String component;

    private String menuRedirect;

    private MenuMeta meta;

    private Long parentId;

    private Set<MenuTreeDTO> children=new HashSet<>();

}
