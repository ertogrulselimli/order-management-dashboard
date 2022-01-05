package com.ertogrul.omsb2b.service.services;

import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.persistence.entities.MeasurementUnit;
import com.ertogrul.omsb2b.persistence.repositories.MeasurementUnitRepository;
import com.ertogrul.omsb2b.service.dtos.measureunit.MeasurementUnitDto;
import com.ertogrul.omsb2b.service.mappers.MeasurementUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ertogrul Selimli on 10/2/2021
 * @project IntelliJ IDEA
 */
@Service
public class MeasurementUnitService {

    @Autowired
    MeasurementUnitRepository repository;

    @Autowired
    MeasurementUnitMapper measurementUnitMapper;


    @Transactional(readOnly = true)
    public List<MeasurementUnitDto> listAll(){
        final List<MeasurementUnit> all = repository.findAll();
       return measurementUnitMapper.toDtos(all);
    }

    @Transactional
    public void newMeasurementUnit(MeasurementUnitDto dto){
        final MeasurementUnit measurementUnit = measurementUnitMapper.toEntity(dto);
        repository.save(measurementUnit);
    }


    @Transactional(readOnly = true)
    public MeasurementUnitDto getById(final Long id){
        final MeasurementUnit measurementUnit = repository.findById(id).orElseThrow(() -> new RuntimeException("Unit not found with id " + id));
        return measurementUnitMapper.toDto(measurementUnit);
    }



    @Transactional
    public void editMeasurementUnit(MeasurementUnitDto dto){
        final MeasurementUnit measurementUnit = repository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Id not found"));
        measurementUnitMapper.update(dto,measurementUnit);
        repository.save(measurementUnit);
    }


    @Transactional(readOnly = true)
    public Page<MeasurementUnitDto> datatable(DatatableRequest<Object> request){
        final Page<MeasurementUnit> all = repository.datatable(request);
        return   all.map(t->measurementUnitMapper.toDto(t));
    }


    @Transactional
    public void deleteMeasurement(final Long id){
          repository.deleteById(id);
    }

}
