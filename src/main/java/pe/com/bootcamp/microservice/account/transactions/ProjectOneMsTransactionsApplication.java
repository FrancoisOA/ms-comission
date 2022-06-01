package pe.com.bootcamp.microservice.account.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProjectOneMsTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectOneMsTransactionsApplication.class, args);
	}

}