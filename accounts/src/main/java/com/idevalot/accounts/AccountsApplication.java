package com.idevalot.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "SoloBank Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Kritawit Y.",
						email = "kritawit.bu@gmail.com",
						url = "https://idevalot.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://idevalot.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "SoloBank Accounts Documentation",
				url = "http://localhost:8080/swagger-ui/index.html"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
