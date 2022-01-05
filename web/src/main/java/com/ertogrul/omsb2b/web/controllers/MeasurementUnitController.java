package com.ertogrul.omsb2b.web.controllers;

import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.service.dtos.measureunit.MeasurementUnitDto;
import com.ertogrul.omsb2b.service.services.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ertogrul Selimli on 10/13/2021
 * @project IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/admin/api")
public class MeasurementUnitController {


    @Autowired
    MeasurementUnitService measurementUnitService;


    @PreAuthorize("endpointAccess('ListMeasurementUnit')")
    @PostMapping(value = "/unit-list")
    public ResponseEntity<Page<MeasurementUnitDto>> listUnits(@RequestBody DatatableRequest<Object> request){
        return ResponseEntity.ok(measurementUnitService.datatable(request));
    }


    @PreAuthorize("endpointAccess('ViewMeasurementUnit')")
    @GetMapping(value = "/unit/{id}")
    public ResponseEntity<MeasurementUnitDto> viewUnit(@PathVariable("id") final Long id){
        return ResponseEntity.ok(measurementUnitService.getById(id));
    }


    @GetMapping(value = "/unit/all")
    public List<MeasurementUnitDto> allUnits(){
        return measurementUnitService.listAll();
    }


    @ResponseBody
    @PreAuthorize("endpointAccess('NewMeasurementUnit')")
    @PostMapping(value = "/unit")
    public void newUnit(@RequestBody MeasurementUnitDto n){
        measurementUnitService.newMeasurementUnit(n);
    }


    @PreAuthorize("endpointAccess('EditMeasurementUnit')")
    @PostMapping(value = "/unit/edit")
    @ResponseBody
    public void editUnit(@RequestBody MeasurementUnitDto request){
        measurementUnitService.editMeasurementUnit(request);
    }



    @PreAuthorize("endpointAccess('DeleteMeasurementUnit')")
    @PostMapping(value = "/unit/delete/{id}")
    public void deleteUnit(@PathVariable("id") final Long id){
        measurementUnitService.deleteMeasurement(id);
    }
}
