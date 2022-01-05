package com.ertogrul.omsb2b.persistence.repositories;

import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CustomRepository<T,ID extends Serializable>  extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

    List<T> findAll(Specification<T> spec, EntityGraph.EntityGraphType entityGraphType, String entityGraphName);

    Page<T> findAll(Specification<T> spec, Pageable pageable, EntityGraph.EntityGraphType entityGraphType, String entityGraphName);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    List<T> findAll(Specification<T> spec, Sort sort, EntityGraph.EntityGraphType entityGraphType, String entityGraphName);

    Optional<T> findOne(Specification<T> spec, EntityGraph.EntityGraphType entityGraphType, String entityGraphName);

    Page<T> datatable(Specification<T> spec, DatatableRequest datatableRequest);

    Page<T> datatable(Specification<T> spec, DatatableRequest datatableRequest, EntityGraph.EntityGraphType entityGraphType,String entityGraphName);

    Page<T> datatable(DatatableRequest datatableRequest);

    List<T> findAllFiltered(Specification<T> spec, Sort sort);

    List<T> findAllFiltered(Specification<T> spec, Sort sort, EntityGraph.EntityGraphType entityGraphType, String entityGraphName);

}
