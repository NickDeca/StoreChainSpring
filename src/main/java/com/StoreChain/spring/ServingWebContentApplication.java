package com.StoreChain.spring;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.StoreChain.spring.Controller"})
@EntityScan("com.StoreChain.spring.model")
@EnableJpaRepositories("com.StoreChain.spring.Repository")
public class ServingWebContentApplication {

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }
	
    public static void main(String[] args) {
    	//System.exit(SpringApplication.exit(
    	SpringApplication.run(ServingWebContentApplication.class, args);
    			//));
    }
}