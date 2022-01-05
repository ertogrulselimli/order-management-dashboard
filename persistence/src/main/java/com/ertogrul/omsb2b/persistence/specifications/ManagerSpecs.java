package com.ertogrul.omsb2b.persistence.specifications;


import com.ertogrul.omsb2b.common.utils.CommonUtils;
import com.ertogrul.omsb2b.persistence.entities.Manager;
import com.ertogrul.omsb2b.persistence.entities.Manager_;
import org.springframework.data.jpa.domain.Specification;

public final class ManagerSpecs {


    public static Specification<Manager> firstNameLike(final String firstName){
        final String like = CommonUtils.getContainsLikePattern(firstName);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Manager_.firstname)),like);
    }


    public static Specification<Manager> lastNameLike(final String lastName){
        final String like = CommonUtils.getContainsLikePattern(lastName);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Manager_.lastname)),like);
    }

    public static Specification<Manager> userNameLike(final String username){
        final String like = CommonUtils.getContainsLikePattern(username);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Manager_.username)),like);
    }


}
