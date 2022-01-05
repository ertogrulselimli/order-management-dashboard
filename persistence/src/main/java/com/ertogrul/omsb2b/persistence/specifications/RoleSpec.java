package com.ertogrul.omsb2b.persistence.specifications;

import com.ertogrul.omsb2b.persistence.entities.ManagerRole;
import com.ertogrul.omsb2b.persistence.entities.ManagerRole_;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Ertogrul Selimli on 10/17/2021
 * @project IntelliJ IDEA
 */
public  final class RoleSpec {

    public static Specification<ManagerRole> name(final String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ManagerRole_.name),name);
    }
}
