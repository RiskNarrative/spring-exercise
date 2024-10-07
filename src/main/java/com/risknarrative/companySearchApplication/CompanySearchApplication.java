package com.risknarrative.companySearchApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan
@Configuration
@EnableAutoConfiguration
@EnableFeignClients
public class CompanySearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanySearchApplication.class, args);
	}

}
