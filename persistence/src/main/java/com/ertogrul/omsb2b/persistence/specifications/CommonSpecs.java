package com.ertogrul.omsb2b.persistence.specifications;

import org.springframework.data.jpa.domain.Specification;

public class CommonSpecs {

    public static <T> Specification<T> emptySpec(Class<T> tClass){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and();
    }
}
