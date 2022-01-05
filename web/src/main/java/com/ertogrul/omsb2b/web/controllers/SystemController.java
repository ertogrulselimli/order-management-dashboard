package com.ertogrul.omsb2b.web.controllers;

import com.ertogrul.omsb2b.service.dtos.measureunit.MeasurementUnitDto;
import com.ertogrul.omsb2b.service.services.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ertogrul Selimli on 10/2/2021
 * @project IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/admin/api")
public class SystemController {

    @Autowired
    MeasurementUnitService service;

    @GetMapping(value = "/units")
    public List<MeasurementUnitDto> listUnits(){
      return null;
    }


}
