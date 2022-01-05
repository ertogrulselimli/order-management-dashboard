package com.ertogrul.omsb2b.persistence.datatable;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DatatableRequest<T> {


    private T columnFilters;

    private List<SortOptions> sortOptions;

    private String searchTerm;

    private int page;

    private int perPage;



}
