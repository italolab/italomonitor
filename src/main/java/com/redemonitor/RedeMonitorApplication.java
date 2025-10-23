package com.redemonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class RedeMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedeMonitorApplication.class, args);
	}

}
