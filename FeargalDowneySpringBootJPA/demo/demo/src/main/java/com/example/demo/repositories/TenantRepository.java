package com.example.demo.repositories;

import com.example.demo.controllers.DTOS.PropertyAddressTenantNameDTO;
import com.example.demo.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    List<Tenant> findAllByOrderByProperty_PropertyAddress();

    @Query("select new com.example.demo.controllers.DTOS.PropertyAddressTenantNameDTO(t.property.propertyAddress, t.tenantName) from Tenant t")
    List<PropertyAddressTenantNameDTO> findAllPropertyAndTenantNames();

    @Query("select t from Tenant t where t.property.propertyEircode = :eircode")
    List<Tenant> findByPropertyEircode(@Param("eircode") int eircode);


}
