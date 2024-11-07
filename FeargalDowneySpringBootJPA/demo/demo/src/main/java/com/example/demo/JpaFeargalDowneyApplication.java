package com.example.demo;


import com.example.demo.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaFeargalDowneyApplication implements CommandLineRunner {

	private PropertyRepository propertyRepository;

	@Autowired
	public JpaFeargalDowneyApplication(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(JpaFeargalDowneyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Property dublin = propertyRepository.save(new Property("Dublin", 1391000));
//		Property cork = propertyRepository.save(new Property("Cork", 542868));
//		Property galway = propertyRepository.save(new Property("Galway", 258058));
//		Property kerry = propertyRepository.save(new Property("Kerry", 147707));
//		Property limerick = propertyRepository.save(new Property("Limerick", 194899));
//		propertyRepository.findByCountyName("Cork").ifPresent(System.out::println);
//		propertyRepository.findByCountyNameIgnoreCase("dublin").ifPresent(System.out::println);
//
//		propertyRepository.findAllByOrderByCountyNameDesc().forEach(System.out::println);
	}
}
