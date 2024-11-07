package com.example.demo;

import com.example.demo.entities.Property;
import com.example.demo.entities.MyUser;
import com.example.demo.entities.Tenant;
import com.example.demo.repositories.PropertyRepository;
import com.example.demo.repositories.MyUserRepository;
import com.example.demo.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    private final PropertyRepository propertyRepository;
    private final TenantRepository tenantRepository;
    private final MyUserRepository myUserRepository;

    @Autowired
    public DataLoader(PropertyRepository propertyRepository, TenantRepository tenantRepository, MyUserRepository myUserRepository) {
        this.propertyRepository= propertyRepository;
        this.tenantRepository = tenantRepository;
        this.myUserRepository = myUserRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Property house1 = propertyRepository.save(new Property("14 NewPark, Clonakilty, Cork", 8, 550));
        Property house2 = propertyRepository.save(new Property("11 BlueStreet, Bishopstown, Cork", 3, 600));
        Property house3 = propertyRepository.save(new Property("SeaBreeze Sky, Rockworth, Dublin", 7, 850));
        Property house4 = propertyRepository.save(new Property("Red Rose Resort, Dingle, Kerry", 4, 500));
        Property house5 = propertyRepository.save(new Property("Tatooine, Tunisia", 10, 1200));


        tenantRepository.save(new Tenant("James Murphy", house1, 42345678));
        tenantRepository.save(new Tenant("Jeff Jones", house2, 27654321));
        tenantRepository.save(new Tenant("John Murphy", house2, 13654321));
        tenantRepository.save(new Tenant("Ted Mac", house2, 34354321));
        tenantRepository.save(new Tenant("Jerry McCarthy", house3, 0022234));
        tenantRepository.save(new Tenant("Sarah Slytherin", house4, 777));
        tenantRepository.save(new Tenant("Peter Parker", house5, 12341034));
        tenantRepository.save(new Tenant("Tony Stark", house5, 12345554));
        tenantRepository.save(new Tenant("Luke Skywalker", house5, 12341232));
        tenantRepository.save(new Tenant("Anakin Sywalker", house5, 44444));
        tenantRepository.save(new Tenant("Padme Skywalker", house5, 412415));
        tenantRepository.save(new Tenant("Leia Organa Skywalker", house1, 000003333));
        tenantRepository.save(new Tenant("Tim Hughes", house3, 777777));
        tenantRepository.save(new Tenant("Sean Varian", house1, 11122112));
        tenantRepository.save(new Tenant("Luke Phair", house1, 0101010101));
        tenantRepository.save(new Tenant("Noah Snyder", house1, 242424242));
        tenantRepository.save(new Tenant("Feargal Downey", house3, 343431));
        tenantRepository.save(new Tenant("Isaac Wolfe", house3, 151515));
        tenantRepository.save(new Tenant("Gwen Stacey", house5, 282828282));
        tenantRepository.save(new Tenant("Darth Vader", house5, 2516173));


        // Finding all including tenants
        List<Property> properties = propertyRepository.findAllIncludingTenants();
        for (Property p: properties)
        {
            System.out.println(p.getPropertyAddress().toUpperCase());
            System.out.println("____________________________");
            for(Tenant t: p.getTenants())
                System.out.println("\t" + t.getTenantName());
            System.out.println("____________________________");

        }

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        myUserRepository.save(new MyUser("manager@mtu.ie", passwordEncoder.encode("secret"), "MNGR", false, 11122233, "K1234567"));
        myUserRepository.save(new MyUser("staff@mtu.ie", passwordEncoder.encode("secret"), "STAFF", false, 22244455, "J121212"));
        myUserRepository.save(new MyUser("lockedout@mtu.ie", passwordEncoder.encode("secret"), "MNGR", true, 57422245, "P332513"));


    }
}
