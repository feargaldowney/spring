package com.example.demo.controllers;

import com.example.demo.controllers.DTOS.NameAndCountDTO;
import com.example.demo.controllers.DTOS.NewPropertyDTO;
import com.example.demo.controllers.DTOS.NewRentDTO;
import com.example.demo.controllers.handlers.ResourceNotFoundException;
import com.example.demo.entities.Property;
import com.example.demo.repositories.PropertyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    @Autowired private PropertyRepository propertyRepository;
    @GetMapping({"/", ""})
    List<Property> findAll(){
        return propertyRepository.findAll();
    }

    @GetMapping({"/withTenants"})
    List<Property> findAllIncludingTenants(){
        return propertyRepository.findAllIncludingTenants();
    }

    @GetMapping({"/{eircode}"})
    Property findById(@PathVariable("eircode") int propertyEircode){
         Optional<Property> propertyOptional = propertyRepository.findById(propertyEircode);
         return propertyOptional.orElseThrow(()-> new ResourceNotFoundException("Property with EirCode " + propertyEircode  + " was not found"));
    }

    @GetMapping({"/availability"})
    List<Property> findPropertyWithAvailability() {
        List<Property> properties = propertyRepository.findPropertyWithAvailability();
        if (properties != null) {
            return properties;
        } else {
            throw new ResourceNotFoundException("No properties have availability right now, sorry for the inconvenience.");
        }
    }

    @GetMapping("/{eircode}/tenantCount")
    NameAndCountDTO getPropertyWithTenantCount(@PathVariable("eircode") int propertyEircode) {
        return propertyRepository.findPropertyWithTenantCountByEircode(propertyEircode)
                .orElseThrow(() -> new ResourceNotFoundException("Property with EirCode " + propertyEircode  + " was not found"));
    }

    // With the presumption of total rent as each tenant * their rate - excluding spare capacity in properties
    @GetMapping("/totalRentalIncome")
    public Long getTotalRentalIncome() {
        return propertyRepository.calculateTotalRentalIncome();
    }

    @PatchMapping("/{eircode}/rent")
    Property updateRent(@Valid @RequestBody NewRentDTO newRentDTO, @PathVariable("eircode") int propertyEircode)
    {
        if (propertyRepository.existsById(propertyEircode))
        {
            propertyRepository.updatePropertyRent(propertyEircode, newRentDTO.newRent());
            return propertyRepository.findById(propertyEircode).get();
        }
        throw new ResourceNotFoundException("Property with EirCode " + propertyEircode  + " was not found");
    }

    @DeleteMapping("/{eircode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("eircode") int propertyEircode){
        if (propertyRepository.existsById(propertyEircode))
            propertyRepository.deleteById(propertyEircode);
        else
            throw new ResourceNotFoundException("Property with EirCode " + propertyEircode  + " was not found");
    }

    @PostMapping({"/",""})
    Property addNewProperty(@RequestBody NewPropertyDTO newPropertyDTO){
        return propertyRepository.save(new Property(newPropertyDTO.propertyAddress(), newPropertyDTO.propertyCapacity(), newPropertyDTO.propertyRent()));
    }
}


