package com.ertogrul.omsb2b.service.services;


import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.persistence.entities.Product;
import com.ertogrul.omsb2b.persistence.entities.ProductRawMaterial;
import com.ertogrul.omsb2b.persistence.repositories.MeasurementUnitRepository;
import com.ertogrul.omsb2b.persistence.repositories.ProductRawMaterialRepository;
import com.ertogrul.omsb2b.persistence.repositories.ProductRepository;
import com.ertogrul.omsb2b.persistence.repositories.RawMaterialRepository;
import com.ertogrul.omsb2b.service.dtos.product.EditProduct;
import com.ertogrul.omsb2b.service.dtos.product.ListProduct;
import com.ertogrul.omsb2b.service.dtos.product.NewProduct;
import com.ertogrul.omsb2b.service.dtos.product.ViewProduct;
import com.ertogrul.omsb2b.service.mappers.MeasurementUnitMapper;
import com.ertogrul.omsb2b.service.mappers.ProductMapper;
import com.ertogrul.omsb2b.service.mappers.RawMaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProductService {

 
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RawMaterialRepository rawMaterialRepository;

    @Autowired
    MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    ProductRawMaterialRepository productRawMaterialRepository;

    @Autowired
    MeasurementUnitMapper measurementUnitMapper;


    @Autowired
    ProductMapper productMapper;

    @Autowired
    RawMaterialMapper rawMaterialMapper;


    @Transactional
    public void newProduct(NewProduct n){
        Product product = productMapper.toProduct(n);
        product = productRepository.save(product);
        Product finalProduct = product;
        n.getRawMaterials().forEach(c->{
           ProductRawMaterial productRawMaterial=new ProductRawMaterial();
           productRawMaterial.setProduct(finalProduct);
           productRawMaterial.setRawMaterial(rawMaterialRepository.findById(c.getMaterialId()).orElseThrow(()->new RuntimeException("Material not found with id "+c.getMaterialId())));
           productRawMaterial.setQuantity(c.getMaterialQuantity());
           productRawMaterial.setUnit(measurementUnitMapper.toEntity(c.getMeasurementUnit()));
           productRawMaterialRepository.save(productRawMaterial);
       });
    }



    @Transactional
    public void editProduct(EditProduct e){
        Product product = productRepository.findById(e.getId()).orElseThrow(() -> new RuntimeException("Product Not found"));
        productMapper.editProduct(e,product);
        product=productRepository.save(product);
        final List<ProductRawMaterial> byProductNative = productRawMaterialRepository.findByProductNative(e.getId());
        productRawMaterialRepository.deleteAll(byProductNative);
        productRawMaterialRepository.flush();
        Product finalProduct=product;
        e.getRawMaterials().forEach(c->{
            ProductRawMaterial productRawMaterial=new ProductRawMaterial();
            productRawMaterial.setProduct(finalProduct);
            productRawMaterial.setRawMaterial(rawMaterialRepository.findById(c.getMaterialId()).orElseThrow(()->new RuntimeException("Material not found with id "+c.getMaterialId())));
            productRawMaterial.setQuantity(c.getMaterialQuantity());
            productRawMaterial.setUnit(measurementUnitMapper.toEntity(c.getMeasurementUnit()));
            productRawMaterialRepository.save(productRawMaterial);
        });

    }



    @Transactional(readOnly = true)
    public ViewProduct findById(Long id){
        final Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        final List<ProductRawMaterial> productRawMaterials =
                productRawMaterialRepository.findByProductNative(id);
        final ViewProduct viewProduct = productMapper.toViewProduct(product);
        viewProduct.setRawMaterials(productRawMaterials.parallelStream().map(t->rawMaterialMapper.toDto(t)).collect(Collectors.toList()));
        return  viewProduct;
    }



    @Transactional(readOnly = true)
    public Page<ListProduct> datatable(DatatableRequest<Object> request) {
        final Page<Product> datatable = productRepository.datatable(request);
        return datatable.map(t -> productMapper.toListProduct(t));
    }






}
