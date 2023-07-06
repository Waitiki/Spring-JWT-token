package com.naina.k.security;

import com.naina.k.security.auth.AuthenticationService;
import com.naina.k.security.auth.RegisterRequest;
import com.naina.k.security.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.naina.k.security.user.Role.ACCOUNTANT;
import static com.naina.k.security.user.Role.ADMIN;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	){
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("JOTA")
					.lastname("TAJO")
					.email("ten.gmail.com")
					.password("ten")
					.role(ADMIN)
					.build();
			System.out.println("Admin token " + service.register(admin).getToken());

			var accountant = RegisterRequest.builder()
					.firstname("KEITA")
					.lastname("TAIKE")
					.email("nine.gmail.com")
					.password("nine")
					.role(ACCOUNTANT)
					.build();
			System.out.println("Accountant token " + service.register(accountant).getToken());
		};
	}
}
