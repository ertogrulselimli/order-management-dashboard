package com.ertogrul.omsb2b.web.controllers;


import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.service.dtos.distributors.EditDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.ListDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.NewDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.ViewDistributor;
import com.ertogrul.omsb2b.service.services.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/admin/api")
public class DistributorController {


    @Autowired
    DistributorService distributorService;


    /*  Start  managers part  dsds*/
    @PreAuthorize("endpointAccess('ListDistributor')")
    @PostMapping(value = "/dst-list")
    public ResponseEntity<Page<ListDistributor>> listDistributors(@RequestBody DatatableRequest<Object> request){
        return ResponseEntity.ok(distributorService.datatable(request));
    }


    @PreAuthorize("endpointAccess('ViewDistributor')")
    @GetMapping(value = "/distributor/{id}")
    public ResponseEntity<ViewDistributor> viewDistributor(@PathVariable("id") final Long id){
        return ResponseEntity.ok(distributorService.findById(id));
    }


    @ResponseBody
    @PreAuthorize("endpointAccess('NewDistributor')")
    @PostMapping(value = "/distributor")
    public void createManager(@RequestBody NewDistributor n){
        distributorService.newDistributor(n);
    }



    @PreAuthorize("endpointAccess('EditDistributor')")
    @PostMapping(value = "/distributor/edit")
    @ResponseBody
    public void editDistributor(@RequestBody EditDistributor request){
        distributorService.editDistributor(request);
    }



    @PreAuthorize("endpointAccess('DeleteDistributor')")
    @ResponseBody
    @PostMapping(value = "/distributor/delete/{id}")
    public void deleteDistributor(@PathVariable("id") final Long id){

    }


    @GetMapping(value = "/dstb-select")
    public List<ListDistributor> selectDstb(){
        return distributorService.select();
    }


}
