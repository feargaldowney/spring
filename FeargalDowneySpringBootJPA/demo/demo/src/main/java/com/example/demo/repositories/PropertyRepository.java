package com.example.demo.repositories;

import com.example.demo.controllers.DTOS.NameAndCountDTO;
import com.example.demo.entities.Property;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    // Derived query
    // The PropertyName that follows findBy"PropertyName" must match its variable name in Property class
    // if it was initially cName it would be findBycName
    Optional<Property> findByPropertyAddress(String address);
    List<Property> findAllByOrderByPropertyAddressDesc();
    Optional<Property> findByPropertyAddressIgnoreCase(String address);
    List<Property> findAllByOrderByPropertyCapacityAsc();

    @Modifying
    @Transactional
    @Query("update Property p set p.propertyRent = :rent where p.propertyEircode = :eircode")
    int updatePropertyRent(@Param("eircode") int eircode, @Param("rent") int rent);
    //NOTE: Can only return void or int upon updating as you
    // can only return the number of rows of which you have updated

    // JPQL - Java persistence query language
    // no wildcard in JPQL

    @Transactional
    @Query("select new com.example.demo.controllers.DTOS.NameAndCountDTO(p.propertyAddress, size(p.tenants)) from Property p where p.propertyEircode = :eircode")
    Optional<NameAndCountDTO> findPropertyWithTenantCountByEircode(@Param("eircode") int eircode);

    @Query("select p from Property p where (select count(t) from Tenant t where t.property = p) < p.propertyCapacity")
    List<Property> findPropertyWithAvailability();

    @Transactional
    @Query("select p from Property p left join fetch p.tenants order by p.propertyAddress")
    List<Property> findAllIncludingTenants();

    @Query("select sum(p.propertyRent * size(p.tenants)) from Property p")
    Long calculateTotalRentalIncome();

}
