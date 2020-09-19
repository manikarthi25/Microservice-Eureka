package com.manikarthi25.eureka.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.manikarthi25.eureka.user.helper.FeignErrorDecode;

import feign.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class EurekaClientUserApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientUserApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	@Profile("production")
	Logger.Level getFeignProductionLogger() {
		return Logger.Level.NONE;
	}

	@Bean
	@Profile("!production")
	Logger.Level getFeignNotProductionLogger() {
		return Logger.Level.FULL;
	}

	@Bean
	public FeignErrorDecode getFeignErrorDecode() {
		return new FeignErrorDecode();
	}

	@Bean
	@Profile("production")
	public String createProductionProfileBean() {
		System.out.println("Production Bean = " + environment.getProperty("myapplication.environment"));
		return "Production Bean created ";
	}

	@Bean
	@Profile("!production")
	public String createNotProductionProfileBean() {
		System.out.println("Not Production Bean = " + environment.getProperty("myapplication.environment"));
		return "Not Production Bean created ";
	}

	@Bean
	@Profile("default")
	public String createDefaultProfileBean() {
		System.out.println("Default Bean = " + environment.getProperty("myapplication.environment"));
		return "Default Bean created ";
	}

}