package com.StoreChain.spring.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ExitController {

	@Autowired 
	private ApplicationContext context;
	
	private void initiateShutDown(int returnCode) {
		SpringApplication.exit(context, () -> returnCode);
	}
	
	@GetMapping("/exit")
	public void greeting() {
				initiateShutDown(0);
	}
}
