package com.ertogrul.omsb2b.web.controllers;

import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.ListRawMaterial;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.NewRawMaterial;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.RawMaterialDto;
import com.ertogrul.omsb2b.service.services.RawMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ertogrul Selimli on 10/13/2021
 * @project IntelliJ IDEA
 */


@RestController
@RequestMapping(value = "/admin/api")
public class RawMaterialController {

    @Autowired
    RawMaterialService rawMaterialService;


    @PreAuthorize("endpointAccess('ListRawMaterial')")
    @PostMapping(value = "/material-list")
    public ResponseEntity<Page<ListRawMaterial>> list(@RequestBody DatatableRequest<Object> request){
        return ResponseEntity.ok(rawMaterialService.datatable(request));
    }



    @PreAuthorize("endpointAccess('ViewRawMaterial')")
    @GetMapping(value = "/material/{id}")
    public ResponseEntity<RawMaterialDto> viewMaterial(@PathVariable("id") final Long id){
        return ResponseEntity.ok(rawMaterialService.getById(id));
    }



    @ResponseBody
    @PreAuthorize("endpointAccess('NewRawMaterial')")
    @PostMapping(value = "/material")
    public void newRawMaterial(@RequestBody NewRawMaterial n){
        rawMaterialService.newRawMaterial(n);
    }



    @PreAuthorize("endpointAccess('EditRawMaterial')")
    @PostMapping(value = "/material/edit")
    @ResponseBody
    public void editRawMaterial(@RequestBody RawMaterialDto request){
        rawMaterialService.editRawMaterial(request);
    }


}
