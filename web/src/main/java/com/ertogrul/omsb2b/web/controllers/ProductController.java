package com.ertogrul.omsb2b.web.controllers;


import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.service.dtos.product.EditProduct;
import com.ertogrul.omsb2b.service.dtos.product.ListProduct;
import com.ertogrul.omsb2b.service.dtos.product.NewProduct;
import com.ertogrul.omsb2b.service.dtos.product.ViewProduct;
import com.ertogrul.omsb2b.service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/api")
public class ProductController {
    
    
    @Autowired
    ProductService productService;

    @PreAuthorize("endpointAccess('ListProduct')")
    @PostMapping(value = "/product-list")
    public ResponseEntity<Page<ListProduct>> listProducts(@RequestBody DatatableRequest<Object> request){
        return ResponseEntity.ok(productService.datatable(request));
    }



    @PreAuthorize("endpointAccess('ViewProduct')")
    @GetMapping(value = "/product/{id}")
    public ResponseEntity<ViewProduct> viewProduct(@PathVariable("id") final Long id){
        return ResponseEntity.ok(productService.findById(id));
    }


    @ResponseBody
    @PreAuthorize("endpointAccess('NewProduct')")
    @PostMapping(value = "/product")
    public void createManager(@RequestBody NewProduct n){
        productService.newProduct(n);
    }



    @PreAuthorize("endpointAccess('EditProduct')")
    @PostMapping(value = "/product/edit")
    @ResponseBody
    public void editProduct(@RequestBody EditProduct request){
        productService.editProduct(request);
    }

}
