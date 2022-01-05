package com.ertogrul.omsb2b.service.services;

import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.persistence.entities.RawMaterial;
import com.ertogrul.omsb2b.persistence.repositories.RawMaterialRepository;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.ListRawMaterial;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.NewRawMaterial;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.RawMaterialDto;
import com.ertogrul.omsb2b.service.mappers.RawMaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ertogrul Selimli on 10/12/2021
 * @project IntelliJ IDEA
 */
@Service
public class RawMaterialService {


    @Autowired
    RawMaterialRepository repository;

    @Autowired
    RawMaterialMapper rawMaterialMapper;


    @Transactional
    public void newRawMaterial(NewRawMaterial dto){
        final RawMaterial measurementUnit = rawMaterialMapper.toEntity(dto);
        repository.save(measurementUnit);
    }



    @Transactional(readOnly = true)
    public RawMaterialDto getById(final Long id){
        final RawMaterial rawMaterial = repository.findById(id).orElseThrow(() -> new RuntimeException("Unit not found with id " + id));
        return rawMaterialMapper.toDto(rawMaterial);
    }



    @Transactional
    public void editRawMaterial(RawMaterialDto dto){
        final RawMaterial measurementUnit = repository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Id not found"));
        rawMaterialMapper.edit(dto,measurementUnit);
        repository.save(measurementUnit);
    }

    @Transactional(readOnly = true)
    public Page<ListRawMaterial> datatable(DatatableRequest<Object> request) {
        final Page<RawMaterial> datatable = repository.datatable(request);
         return datatable.map(t -> rawMaterialMapper.toListDto(t));
    }



   /* @Transactional(readOnly = true)
    public List<RawMaterialDto> list(){
        final List<RawMaterial> all = repository.findAll();
        return measurementUnitMapper.toDtos(all);
    }*/




}
