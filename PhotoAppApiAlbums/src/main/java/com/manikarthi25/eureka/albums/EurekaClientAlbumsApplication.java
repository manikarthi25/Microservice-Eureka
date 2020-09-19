package com.manikarthi25.eureka.albums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientAlbumsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientAlbumsApplication.class, args);
	}

}

