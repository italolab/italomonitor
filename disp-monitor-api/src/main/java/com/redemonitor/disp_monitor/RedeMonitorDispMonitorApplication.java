package com.redemonitor.disp_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class RedeMonitorDispMonitorApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RedeMonitorDispMonitorApplication.class, args);
	}

}
