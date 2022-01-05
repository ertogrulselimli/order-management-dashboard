package com.ertogrul.omsb2b.web.controllers;


import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.EditManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.ListManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.NewManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.ViewManufacturePoint;
import com.ertogrul.omsb2b.service.services.ManufacturePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/api")
public class ManufacturePointController {



    @Autowired
    ManufacturePointService manufacturePointService;



    @GetMapping(value = "/mnfp-select")
    public List<ListManufacturePoint> selectMnfps(){
        return manufacturePointService.select();
    }


    /*  Start  managers part  dsds*/
    @PreAuthorize("endpointAccess('ListManufacturePoint')")
    @PostMapping(value = "/mnfp-list")
    public ResponseEntity<Page<ListManufacturePoint>> listManufacturePoints(@RequestBody DatatableRequest<Object> request){
        return ResponseEntity.ok(manufacturePointService.datatable(request));
    }


    @PreAuthorize("endpointAccess('ViewManufacturePoint')")
    @GetMapping(value = "/manufacturePoint/{id}")
    public ResponseEntity<ViewManufacturePoint> viewManufacturePoint(@PathVariable("id") final Long id){
        return ResponseEntity.ok(manufacturePointService.findById(id));
    }


    @ResponseBody
    @PreAuthorize("endpointAccess('NewManufacturePoint')")
    @PostMapping(value = "/manufacturePoint")
    public void createManager(@RequestBody NewManufacturePoint n){
        manufacturePointService.newManufacturePoint(n);
    }


    @PreAuthorize("endpointAccess('EditManufacturePoint')")
    @PostMapping(value = "/manufacturePoint/edit")
    @ResponseBody
    public void editManufacturePoint(@RequestBody EditManufacturePoint request){
        manufacturePointService.editManufacturePoint(request);
    }


}
