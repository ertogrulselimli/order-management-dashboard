package com.ertogrul.omsb2b.service.mappers;


import com.ertogrul.omsb2b.persistence.entities.Product;
import com.ertogrul.omsb2b.service.dtos.product.EditProduct;
import com.ertogrul.omsb2b.service.dtos.product.ListProduct;
import com.ertogrul.omsb2b.service.dtos.product.NewProduct;
import com.ertogrul.omsb2b.service.dtos.product.ViewProduct;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(NewProduct n);

    void editProduct(EditProduct e, @MappingTarget Product d);

    ListProduct toListProduct(Product d);

    ViewProduct toViewProduct(Product d);

}
