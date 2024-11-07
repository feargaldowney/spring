package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name="sameTenantDifferentProperties",
        columnNames = {"tenantName", "property_eircode"}) })
public class Tenant {
    @Id
    @GeneratedValue
    private int tenantEmail;
    @Column(nullable = false)
    private String tenantName;
    @Column(nullable = false, unique = true)
    private int tenantPhone;

    @ManyToOne // This side will have the f-key
    @JoinColumn(name="property_eircode")
    private Property property;

    public Tenant(String tenantName, Property property, int tenantPhone) {
        this.tenantName = tenantName;
        this.property = property;
        this.tenantPhone = tenantPhone;
    }
}
