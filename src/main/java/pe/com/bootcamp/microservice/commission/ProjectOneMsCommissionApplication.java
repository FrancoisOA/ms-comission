package pe.com.bootcamp.microservice.commission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProjectOneMsCommissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectOneMsCommissionApplication.class, args);
	}

}