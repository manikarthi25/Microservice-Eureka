package com.mankarthi25.eureka.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientAccountApplication.class, args);
	}

}
