package com.ertogrul.omsb2b.service.mappers;

import com.ertogrul.omsb2b.persistence.entities.ProductRawMaterial;
import com.ertogrul.omsb2b.persistence.entities.RawMaterial;
import com.ertogrul.omsb2b.service.dtos.product.ProductRawMaterialDto;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.ListRawMaterial;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.NewRawMaterial;
import com.ertogrul.omsb2b.service.dtos.rawmaterial.RawMaterialDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * @author Ertogrul Selimli on 10/13/2021
 * @project IntelliJ IDEA
 */
@Mapper(componentModel = "spring",uses = {MeasurementUnitMapper.class})
public interface RawMaterialMapper {

    RawMaterial toEntity(NewRawMaterial dto);

    void edit(RawMaterialDto dto, @MappingTarget RawMaterial rawMaterial);

    RawMaterialDto toDto(RawMaterial rawMaterial);

    ListRawMaterial toListDto(RawMaterial rawMaterial);


    @Mappings({
            @Mapping(source = "rawMaterial.id",target = "materialId"),
            @Mapping(source = "rawMaterial.name",target = "materialName"),
            @Mapping(source = "quantity",target = "materialQuantity"),
            @Mapping(source = "unit",target = "measurementUnit")
    })
    ProductRawMaterialDto toDto(ProductRawMaterial productRawMaterial);

}
