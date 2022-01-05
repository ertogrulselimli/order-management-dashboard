package com.ertogrul.omsb2b.service.mappers;


import com.ertogrul.omsb2b.persistence.entities.Distributor;
import com.ertogrul.omsb2b.service.dtos.distributors.EditDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.ListDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.NewDistributor;
import com.ertogrul.omsb2b.service.dtos.distributors.ViewDistributor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DistributorMapper {

   Distributor toDistributor(NewDistributor n);

   void editDistributor(EditDistributor e, @MappingTarget Distributor d);

   ListDistributor toListDistributor(Distributor d);

  ViewDistributor toViewDistributor(Distributor d);

  List<ListDistributor> toListDistributors(List<Distributor> ds);

}
