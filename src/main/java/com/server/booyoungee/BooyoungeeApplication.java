package com.server.booyoungee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = {
	@Server(url = "http://post2trip.site", description = "Default Server URL")
})
@SpringBootApplication
public class BooyoungeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooyoungeeApplication.class, args);
	}

}
