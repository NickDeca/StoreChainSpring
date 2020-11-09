package com.StoreChain.spring.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ExitController {

	@Autowired 
	private ApplicationContext context;
	
	private void initiateShutDown(int returnCode) {
		SpringApplication.exit(context, () -> returnCode);
	}
	
	@GetMapping("/exit")
	public RedirectView greeting() {
				initiateShutDown(1);
				//TODO redirect to index
				return new RedirectView("index"); 
	}
}
