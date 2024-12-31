package com.revature.shoply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EntityScan("com.revature.shoply.models")
@ComponentScan("com.revature")
public class ShoplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoplyApplication.class, args);
	}

}
