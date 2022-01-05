package com.ertogrul.omsb2b.service.services;


import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.persistence.entities.ManufacturePoint;
import com.ertogrul.omsb2b.persistence.repositories.ManufacturePointRepository;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.EditManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.ListManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.NewManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.ViewManufacturePoint;
import com.ertogrul.omsb2b.service.mappers.ManufacturePointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManufacturePointService {


    @Autowired
    ManufacturePointRepository mfpRepository;

    @Autowired
    ManufacturePointMapper mfpMapper;


    @Transactional(readOnly = true)
    public List<ListManufacturePoint> select(){
        final List<ManufacturePoint> all = mfpRepository.findAll();
        return mfpMapper.toListDtos(all);
    }


    public void newManufacturePoint(NewManufacturePoint n){
        final ManufacturePoint distributor = mfpMapper.toManufacturePoint(n);
        mfpRepository.save(distributor);
    }


   public ListManufacturePoint  findByName(final String name) {
       final ManufacturePoint dfdf = mfpRepository.findByName(name).orElseThrow(() -> new RuntimeException("dfdf"));
       return  mfpMapper.toListManufacturePoint(dfdf);

   }

    public void editManufacturePoint(EditManufacturePoint e){
        final ManufacturePoint distributor = mfpRepository.findById(e.getId()).orElseThrow(() -> new RuntimeException("ManufacturePoint Not found"));
        mfpMapper.editManufacturePoint(e,distributor);
        mfpRepository.save(distributor);
    }


    @Transactional(readOnly = true)
    public ViewManufacturePoint findById(Long id){
        final ManufacturePoint distributor = mfpRepository.findById(id).orElseThrow(() -> new RuntimeException("ManufacturePoint not found with id " + id));
        return   mfpMapper.toViewManufacturePoint(distributor);
    }

    @Transactional(readOnly = true)
    public Page<ListManufacturePoint> datatable(DatatableRequest<Object> request) {
        final Page<ManufacturePoint> datatable = mfpRepository.datatable(request);
        return datatable.map(t -> mfpMapper.toListManufacturePoint(t));
    }









}
