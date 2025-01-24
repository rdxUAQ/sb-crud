package com.sb.app.sb_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:messages.properties")
public class SbCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbCrudApplication.class, args);
	}

}
