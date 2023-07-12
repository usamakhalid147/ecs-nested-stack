package com.acima.genesiscreditservice;

//import com.acima.genesiscreditservice.dao.UserRepository;
//import com.acima.genesiscreditservice.entities.User;
//import org.hibernate.dialect.MySQLDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class GenesisCreditServiceApplication {

	public static void main(String[] args) {

	SpringApplication.run(GenesisCreditServiceApplication.class, args);

//		UserRepository userRepository = context.getBean(UserRepository.class);
//
//		User user =  new User();
//		user.setName("BiUser");
//		user.setCity("cityname1");
//		user.setStatus("Good");
//
//		User user1 = userRepository.save(user);
	}

}
