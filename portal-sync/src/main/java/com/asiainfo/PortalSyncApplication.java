package com.asiainfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PortalSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalSyncApplication.class, args);
	}

}
