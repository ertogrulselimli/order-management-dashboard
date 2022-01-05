package com.ertogrul.omsb2b.service.mappers;

import com.ertogrul.omsb2b.persistence.entities.ManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.EditManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.ListManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.NewManufacturePoint;
import com.ertogrul.omsb2b.service.dtos.mnfcpoint.ViewManufacturePoint;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManufacturePointMapper {



   ManufacturePoint toManufacturePoint(NewManufacturePoint n);

   void editManufacturePoint(EditManufacturePoint e, @MappingTarget ManufacturePoint d);

   ListManufacturePoint toListManufacturePoint(ManufacturePoint d);

   ViewManufacturePoint toViewManufacturePoint(ManufacturePoint d);

   List<ListManufacturePoint> toListDtos(List<ManufacturePoint> ens);




}
