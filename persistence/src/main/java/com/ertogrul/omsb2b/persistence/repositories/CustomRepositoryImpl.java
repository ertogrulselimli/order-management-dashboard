package com.ertogrul.omsb2b.persistence.repositories;

import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.persistence.datatable.PagingAndSorter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public class CustomRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements CustomRepository<T,ID> {

    private EntityManager em;

    private final Class<T> domainClass;

    public CustomRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.domainClass=domainClass;
        this.em = em;
    }

    public CustomRepositoryImpl(JpaEntityInformation<T,?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.domainClass = entityInformation.getJavaType();
        this.em = em;
    }


    @Override
    public Page<T> datatable(Specification<T> spec, DatatableRequest datatableRequest) {
        final Pageable pageable = PagingAndSorter.parse(datatableRequest);
        TypedQuery<T> query = getQuery(spec, pageable.getSort());
        return readPage(query,domainClass, pageable, spec);
    }


    @Override
    public Page<T> datatable(DatatableRequest datatableRequest) {
        final Pageable page = PagingAndSorter.parse(datatableRequest);
        return findAll(page);
    }


    @Override
    public Page<T> datatable(Specification<T> spec,
                             DatatableRequest datatableRequest,
                             EntityGraph.EntityGraphType entityGraphType,
                             String entityGraphName) {
        final Pageable pageable = PagingAndSorter.parse(datatableRequest);
        TypedQuery<T> query = getQuery(spec, pageable.getSort());
        query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
        return readPage(query,domainClass, pageable, spec);
    }

    @Override
    public List<T> findAllFiltered(Specification<T> spec, Sort sort) {
        final TypedQuery<T> query = getQuery(spec,sort);
        query.setFirstResult(0);
        query.setMaxResults(100);
        return query.getResultList();
    }

    @Override
    public List<T> findAllFiltered(Specification<T> spec, Sort sort, EntityGraph.EntityGraphType entityGraphType, String entityGraphName) {
        final TypedQuery<T> query = getQuery(spec, sort);
        query.setFirstResult(0);
        query.setMaxResults(100);
        query.setHint(entityGraphType.getKey(),em.getEntityGraph(entityGraphName));

        return query.getResultList();
    }

    @Override
    public List<T> findAll(Specification<T> spec, EntityGraph.EntityGraphType entityGraphType, String entityGraphName) {
        TypedQuery<T> query = getQuery(spec, Sort.unsorted());
        query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
        return query.getResultList();
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable, EntityGraph.EntityGraphType entityGraphType, String entityGraphName) {
        TypedQuery<T> query = getQuery(spec, pageable.getSort());
        query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
        return readPage(query,domainClass, pageable, spec);
    }

/*    @Override
    public Page<T> findAll(Specification<T> spec,Pageable pageable){
        TypedQuery<T> query = getQuery(spec, pageable.getSort());
        //query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
        return readPage(query,domainClass, pageable, spec);
    }
    */

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort, EntityGraph.EntityGraphType entityGraphType, String entityGraphName) {
        TypedQuery<T> query = getQuery(spec, sort);
        query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
        return query.getResultList();
    }

    @Override
    public Optional<T> findOne(Specification<T> spec, EntityGraph.EntityGraphType entityGraphType, String entityGraphName) {
       try{
        TypedQuery<T> query = getQuery(spec, Sort.unsorted());
        query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
        return Optional.ofNullable(query.getSingleResult());} catch (EmptyResultDataAccessException | NoResultException e){
           return null;
       }
    }

}
