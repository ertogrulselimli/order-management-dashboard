package com.ertogrul.omsb2b.persistence.datatable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public final class PagingAndSorter {


    public static Pageable parse(DatatableRequest datatableRequest){
        final List<SortOptions> sort = datatableRequest.getSortOptions();
        if(sort!=null && sort.size()>0){
            final List<Sort.Order>   orders = sort.stream().
                    map(t -> new Sort.Order(t.getType().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, t.getField())).
                    collect(Collectors.toList());
            return PageRequest.of(datatableRequest.getPage(),datatableRequest.getPerPage(),Sort.by(orders));
        }
        return   PageRequest.of(datatableRequest.getPage(),datatableRequest.getPerPage());
    }




}
