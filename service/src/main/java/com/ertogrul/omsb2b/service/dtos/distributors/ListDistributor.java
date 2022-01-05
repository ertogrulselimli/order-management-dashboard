package com.ertogrul.omsb2b.service.dtos.distributors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ListDistributor {
    private Long id;

    private String name;
    private String region;

    private String description;
}
