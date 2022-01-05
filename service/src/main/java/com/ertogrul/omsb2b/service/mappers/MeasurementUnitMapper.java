package com.ertogrul.omsb2b.service.mappers;

import com.ertogrul.omsb2b.persistence.entities.MeasurementUnit;
import com.ertogrul.omsb2b.service.dtos.measureunit.MeasurementUnitDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author Ertogrul Selimli on 10/2/2021
 * @project IntelliJ IDEA
 */

@Mapper(componentModel = "spring")
public interface MeasurementUnitMapper {

    MeasurementUnit toEntity(MeasurementUnitDto dto);

    MeasurementUnitDto toDto(MeasurementUnit unit);

    void update(MeasurementUnitDto dto, @MappingTarget MeasurementUnit unit);

    List<MeasurementUnitDto> toDtos(List<MeasurementUnit> entites);



}
