package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Property {
    @Id
    @GeneratedValue
//    @Column(Address="id")
    private int propertyEircode; // primary key
    @Column(unique = true, nullable = false)
    private String propertyAddress;
//    @Column(Address="capacity")
    private int propertyCapacity;
    private int propertyRent;

    // one property to many tenants
    // cascade all means if the property is deleted all associated tenants will also be deleted
    // Fetch type lazy, whenever you have a list use .LAZY. Only gets properties not tenants.
    // if I use .get(tenants) then it will get them
    // because were using lazy we use toString.Exclude to exclude the tenants
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<Tenant> tenants;

    public Property(String propertyAddress, int propertyCapacity, int propertyRent) {
        this.propertyAddress = propertyAddress;
        this.propertyCapacity = propertyCapacity;
        this.propertyRent = propertyRent;
    }
}
