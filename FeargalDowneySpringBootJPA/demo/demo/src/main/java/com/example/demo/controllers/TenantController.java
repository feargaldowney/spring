package com.example.demo.controllers;

import com.example.demo.controllers.DTOS.NewTenantDTO;
import com.example.demo.controllers.DTOS.TenantMoveDTO;
import com.example.demo.controllers.handlers.ResourceNotFoundException;
import com.example.demo.entities.Property;
import com.example.demo.entities.Tenant;
import com.example.demo.controllers.DTOS.PropertyAddressTenantNameDTO;
import com.example.demo.repositories.PropertyRepository;
import com.example.demo.repositories.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {
    private final TenantRepository tenantRepository;
    private final PropertyRepository propertyRepository;
    @Autowired
    public TenantController(TenantRepository tenantRepository, PropertyRepository propertyRepository) {
        this.tenantRepository = tenantRepository;
        this.propertyRepository = propertyRepository;
    }
    //http:localhost:8080/towns/1 returns 200 Ok with Object
    //http:localhost:8080/towns/789 returns 404 Not Found with error message
    @GetMapping("/{eircode}")
    Tenant findById(@PathVariable("eircode") int tenantEmail){
        Optional<Tenant> tenantOptional = tenantRepository.findById(tenantEmail);
        if (tenantOptional.isPresent())
            return tenantOptional.get();
        throw new ResourceNotFoundException("Tenant with " + tenantEmail + " was not found");
    }

    @GetMapping("/residents/{eircode}")
    List<Tenant> getTenantsByPropertyEircode(@PathVariable("eircode") int propertyEircode) {
        return tenantRepository.findByPropertyEircode(propertyEircode);
    }

    @PostMapping({"/",""})
    Tenant addTenant(@RequestBody NewTenantDTO tenantDTO) {
        Property property = propertyRepository.findById(tenantDTO.newPropertyEircode())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        if (property.getTenants().size() < property.getPropertyCapacity()) {
            Tenant newTenant = new Tenant(tenantDTO.newTenantName(), property, tenantDTO.newTenantPhone());
            return tenantRepository.save(newTenant);
        } else {
            throw new IllegalStateException("Property is at full capacity");
        }
    }

    @Transactional
    @PatchMapping("/move")
    public Tenant moveTenant(@RequestBody TenantMoveDTO tenantMoveDTO) {
        Tenant tenant = tenantRepository.findById(tenantMoveDTO.tenantEmail())
                .orElseThrow(() -> new IllegalStateException("Tenant not found"));

        Property newProperty = propertyRepository.findById(tenantMoveDTO.newEircode())
                .orElseThrow(() -> new IllegalStateException("Property not found"));

        if (newProperty.getTenants().size() >= newProperty.getPropertyCapacity()) {
            throw new IllegalStateException("New property is at full capacity");
        }

        tenant.setProperty(newProperty);
        return tenantRepository.save(tenant);
    }
//    @GetMapping("/justnames")
//    List<PropertyAddressTenantNameDTO> findAllPropertyNamesAndTenantNames(){
//        return tenantRepository.findAllPropertyAndTenantNames();
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("id") int tenantEmail){
        if (tenantRepository.existsById(tenantEmail))
            tenantRepository.deleteById(tenantEmail);
        else
            throw new ResourceNotFoundException("Tenant with Email " + tenantEmail  + " was not found");
    }
}
