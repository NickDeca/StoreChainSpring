package com.StoreChain.spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.StoreChain.spring.Repository.StoreRepository;

@Controller
@RequestMapping(path="/Store")
public class StoreController {
	
	@Autowired
	private StoreRepository storeRepository;

	@GetMapping("*")
	public String Index(Model model) {
		
		model.addAttribute("Store", storeRepository.findTopByOrderByIdDesc());
		return "StoreIndex";
	}
}
