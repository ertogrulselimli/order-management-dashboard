package com.ertogrul.omsb2b.service.services;


import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.persistence.entities.Distributor;
import com.ertogrul.omsb2b.persistence.repositories.DistributorRepository;
import com.ertogrul.omsb2b.service.dtos.distributors.EditDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.ListDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.NewDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.ViewDistributor;
import com.ertogrul.omsb2b.service.mappers.DistributorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DistributorService {



    @Autowired
    DistributorMapper dMapper;


    @Autowired
    DistributorRepository dRepository;


    public void newDistributor(NewDistributor n){
        final Distributor distributor = dMapper.toDistributor(n);
        dRepository.save(distributor);
    }



    public void editDistributor(EditDistributor e){
        final Distributor distributor = dRepository.findById(e.getId()).orElseThrow(() -> new RuntimeException("Distributor Not found"));
        dMapper.editDistributor(e,distributor);
        dRepository.save(distributor);
    }



    @Transactional(readOnly = true)
    public ViewDistributor findById(Long id){
        final Distributor distributor = dRepository.findById(id).orElseThrow(() -> new RuntimeException("Distributor not found with id " + id));
        return   dMapper.toViewDistributor(distributor);
    }



    @Transactional(readOnly = true)
    public Page<ListDistributor> datatable(DatatableRequest<Object> request) {
        final Page<Distributor> datatable = dRepository.datatable(request);
        return datatable.map(t -> dMapper.toListDistributor(t));
    }



    @Transactional(readOnly = true)
    public List<ListDistributor> select(){
        final List<Distributor> all = dRepository.findAll();
        return dMapper.toListDistributors(all);
    }





}
