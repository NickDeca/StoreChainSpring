package com.StoreChain.spring.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.StoreChain.spring.Repository.CustomersRepository;
import com.StoreChain.spring.model.Customers;

@Controller
@RequestMapping(path="/Customers")
public class CustomersController {

	@Autowired
	private CustomersRepository customerContext;
	
	@GetMapping("*")
	public String Index() {
		
		return "CustomersIndex";
	}
	
	@GetMapping("/All")
	public @ResponseBody Iterable<Customers> getAllProducts(){
		return customerContext.findAll();
	}
	
	@GetMapping("/Create")
	public String CreateNewCustomerGet(Model model) {
	    model.addAttribute("Customers", new Customers());
		return "CreateCustomer";
	}
	
	@PostMapping(path = "/Create")
	public RedirectView CreateNewCustomer(@ModelAttribute Customers customer, Model model){
			
		customerContext.save(customer);		
		return new RedirectView("CreateCustomer"); 
	}
	
	@GetMapping("/Update")
	public String UpdateCustomerGet(Model model) {
	    model.addAttribute("Customers", new Customers());
		return "UpdateCustomer";
	}
	
	@PostMapping("/Update")
	public @ResponseBody String UpdateCustomer(@ModelAttribute Customers customer, Model model){
		Optional<Customers> updateToBe = customerContext.findById(customer.getId());
		Customers update = null;
		if(updateToBe.isPresent())
			update = updateToBe.get();
		
		if(customer.getCapital() != null)
			update.setCapital(customer.getCapital());
		
		if(customer.getDescription() != null)
			update.setDescription(customer.getDescription());
		
		if(customer.getFirstName() != null)
			update.setFirstName(customer.getFirstName());

		if(customer.getLastName() != null)
			update.setLastName(customer.getLastName());
		
		customerContext.save(update);
		
		return "Updated";
	}
	
	@GetMapping("/Delete")
	public String DeleteCustomerGet(Model model) {
	    model.addAttribute("Customer", new Customers());
		return "DeleteCustomer";
	}
	
	@PostMapping("/Delete")
	public @ResponseBody String DeleteCustomer(@RequestParam int id) {
		
		Optional<Customers> toBeDeleted = customerContext.findById(id);
		Customers customer = null;
		
		if(toBeDeleted.isPresent())
			customer = toBeDeleted.get();
		
		customerContext.delete(customer);
		
		return "Deleted";
	}
}
