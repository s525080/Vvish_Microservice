package com.appdev.vvish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VvishApplication {

	public static void main(String[] args) {
		SpringApplication.run(VvishApplication.class, args);
	}
}
