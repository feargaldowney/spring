package com.example.demo.controllers;

import com.example.demo.entities.Property;
import com.example.demo.entities.Tenant;
import com.example.demo.repositories.PropertyRepository;
import com.example.demo.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLController {
    @Autowired
    private PropertyRepository propertyRepository;
    private TenantRepository tenantRepository;


    @QueryMapping(value = "properties")
    List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    @QueryMapping
    Property findProperty(@Argument int eircode){
        return propertyRepository.findById(eircode).orElse(null);
    }

    @MutationMapping
    @Secured(value="ROLE_MNGR")
    Property createProperty(@Argument("address") String propertyAddress, @Argument("capacity") int capacity, @Argument("rent") int rent) {
        return propertyRepository.save(new Property(propertyAddress, capacity, rent));
    }

    @MutationMapping
    @Secured(value="ROLE_MNGR")
    Tenant createTenant(@Argument("name") String name, @Argument("eircode") Property eircode, @Argument("phone") int phone) {
        return tenantRepository.save(new Tenant(name,  eircode, phone));
    }
}
