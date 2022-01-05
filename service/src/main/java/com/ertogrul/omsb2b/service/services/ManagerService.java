package com.ertogrul.omsb2b.service.services;

import com.ertogrul.omsb2b.common.exception.StandardException;
import com.ertogrul.omsb2b.common.utils.CommonUtils;
import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.persistence.entities.Manager;
import com.ertogrul.omsb2b.persistence.repositories.DistributorRepository;
import com.ertogrul.omsb2b.persistence.repositories.ManagerRepository;
import com.ertogrul.omsb2b.persistence.repositories.ManagerRolesRepository;
import com.ertogrul.omsb2b.persistence.repositories.ManufacturePointRepository;
import com.ertogrul.omsb2b.persistence.specifications.CommonSpecs;
import com.ertogrul.omsb2b.persistence.specifications.ManagerSpecs;
import com.ertogrul.omsb2b.service.dtos.manager.EditManager;
import com.ertogrul.omsb2b.service.dtos.manager.ListManager;
import com.ertogrul.omsb2b.service.dtos.manager.NewManager;
import com.ertogrul.omsb2b.service.dtos.manager.ViewManager;
import com.ertogrul.omsb2b.service.exceptions.ManagerNotFoundException;
import com.ertogrul.omsb2b.service.exceptions.ManagerUsernameExistsException;
import com.ertogrul.omsb2b.service.mappers.ManagerMapper;
import com.ertogrul.omsb2b.service.mappers.MenuMapper;
import com.ertogrul.omsb2b.service.mappers.RoleMapper;
import com.ertogrul.omsb2b.service.security.ManagerPrincipal;
import com.ertogrul.omsb2b.service.services.filters.ManagerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
@Service
public class ManagerService  {


    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    ManagerRolesRepository rolesRepository;

    @Autowired
    DistributorRepository distributorRepository;

    @Autowired
    ManufacturePointRepository manufacturePointRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenBlockListService tokenBlockListService;



    public void changePassword(final String newPassword,final String confirmPassword){
        final ManagerPrincipal principal = (ManagerPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Manager manager = managerRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new StandardException("Problem occured while changing password contact administator"));
        if(!newPassword.equals(confirmPassword)){
            throw new StandardException("Confirm password does not match");
        }
        manager.setPassword(passwordEncoder.encode(newPassword));
        managerRepository.save(manager);
    }



    public ViewManager newManager(NewManager dto) throws ManagerUsernameExistsException {
        //check duplicate username
         managerRepository.findByUsername(dto.getUsername()).ifPresent(manager->{throw new StandardException("manager with this username already exists");});
         //check duplicate email
         managerRepository.findByEmail(dto.getEmail()).ifPresent(t->{throw new StandardException(dto.getEmail()+ " email already exists");});
         Manager manager = managerMapper.toManager(dto);
         manager.setPassword(passwordEncoder.encode(manager.getPassword()));
         Manager finalManager = manager;
         //set Role
         Optional.ofNullable(dto.getRoleId()).ifPresent(t->{
             finalManager.setRole(rolesRepository.findById(t).orElseThrow(()->new RuntimeException("Role not found"+t)));
                 });
          //set Distributor
         Optional.ofNullable(dto.getDistributorId()).ifPresent(t->{
             finalManager.setDistributor(distributorRepository.findById(t).orElseThrow(()->new RuntimeException("Distributor not found "+t)));
         });
         //set Manufacture Point
        Optional.ofNullable(dto.getManufacturePointId()).ifPresent(t->{
            finalManager.setManufacturePoint(manufacturePointRepository.findById(t).orElseThrow(()->new RuntimeException("Manufacture Point not found "+t)));
        });
        Manager  persisted=  managerRepository.save(manager);
        return managerMapper.toViewManagerDTO(persisted);
    }




    public   Manager  findByName(final String username){
         return managerRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("dfsf"));
    }

    @Transactional
    public void blockManager(final Long managerId){
        final Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new StandardException("Manager not found with id " + managerId));
        final String lastJwtToken = manager.getLastJwtToken();
        tokenBlockListService.store(lastJwtToken);
        manager.setActive(false);
        managerRepository.save(manager);
    }



    @Transactional
    public void unBlockManager(final Long managerId){
        final Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new StandardException("Manager not found with id " + managerId));
        manager.setActive(true);
        managerRepository.save(manager);
    }


    public void editManager(EditManager dto){
        final Manager manager = managerRepository.findById(dto.getId()).orElseThrow(() -> new ManagerNotFoundException("Manager not found with id " + dto.getId()));
         managerMapper.editManager(dto,manager);
         manager.setRole(rolesRepository.findById(dto.getRoleId()).orElseThrow(()->new RuntimeException("Role not found"+dto.getRoleId())));
         Optional.ofNullable(dto.getDistributorId()).ifPresent(c->{
            manager.setDistributor(distributorRepository.findById(c).orElseThrow(()->new RuntimeException("Distributor not found with id "+c)));
         });
         Optional.ofNullable(dto.getManufacturePointId()).ifPresent(c->{
            manager.setManufacturePoint(manufacturePointRepository.findById(c).orElseThrow(()->new RuntimeException("Manufacture Point not found with id "+c)));
         });
       //  if (dto.getRole().getName().equals(RoleConstants.DISTRIBUTOR) && dto.getDistributorId() ==null ) //todo
        managerRepository.save(manager);
    }


    public boolean checkFirstTimeAuthenticated(){
        final ManagerPrincipal principal = (ManagerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Optional<Manager> byUsername = managerRepository.findByUsername(principal.getUsername());
        final Manager manager = byUsername.get();
        if (manager.getLastJwtToken()==null ||manager.getLastJwtToken().isEmpty()){
            return true;
        }else{
            return false;
        }
    }


    public void addLastJwtToken(final String username,
                                final String jwtToken){
        final Manager manager = managerRepository.findByUsername(username).orElseThrow(() -> new ManagerNotFoundException("Manager not found with username " + username));
        manager.setLastJwtToken(jwtToken);
        managerRepository.save(manager);
    }



    @Transactional(readOnly = true)
    public ViewManager getManagerById(final Long id){
        final Manager manager = managerRepository.findById(id).orElseThrow(() -> new ManagerNotFoundException("Manager Not found with id " + id));
        return managerMapper.toViewManagerDTO(manager);
    }







    @Transactional(readOnly = true)
    public Page<ListManager> datatable(DatatableRequest<ManagerFilter> request) {
         Specification<Manager> spec = CommonSpecs.emptySpec(Manager.class);
         if(!CommonUtils.isNullOrEmpty(request.getSearchTerm())){
             spec=spec.and(ManagerSpecs.firstNameLike(request.getSearchTerm()).
                     or(ManagerSpecs.lastNameLike(request.getSearchTerm()).or(ManagerSpecs.userNameLike(request.getSearchTerm()))));
         }
        final Page<Manager> datatable = managerRepository.datatable(spec, request);
        return datatable.map(t -> managerMapper.toListManagerDTO(t));
    }



    @Transactional
    public void deleteManager(Long id){
        final Manager manager = managerRepository.findById(id).orElseThrow(() -> new StandardException("Manager not found with id " + id));
        managerRepository.delete(manager);
    }


}
