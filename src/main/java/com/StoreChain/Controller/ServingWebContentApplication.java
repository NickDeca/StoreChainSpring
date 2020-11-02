package com.StoreChain.Controller;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServingWebContentApplication {

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }
	
    public static void main(String[] args) {
    	//System.exit(SpringApplication.exit(
    	ConfigurableApplicationContext  context = SpringApplication.run(ServingWebContentApplication.class, args);
    			//));
    }
}