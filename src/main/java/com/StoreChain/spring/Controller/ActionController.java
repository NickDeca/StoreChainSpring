package com.StoreChain.spring.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.StoreChain.spring.Helper.BuyActionClass;
import com.StoreChain.spring.Helper.HelperMethods;
import com.StoreChain.spring.Repository.CustomersRepository;
import com.StoreChain.spring.Repository.ProductRepository;
import com.StoreChain.spring.model.Customers;
import com.StoreChain.spring.model.Products;


@Controller
@RequestMapping(path="/Actions")
public class ActionController {
	
	@Autowired
	private ProductRepository productContext;
	
	@Autowired
	private CustomersRepository customerContext;

	@GetMapping("*")
	public String Index() {
		
		return "ActionsViews/ActionsIndex";
	}
	
	@GetMapping("/Supply")
	public String SupplyGet(Model model) {
		try {
		    model.addAttribute("Products", productContext.findAll());
		    model.addAttribute("Product", new Products());
			return "ActionsViews/ActionsSupply";
		}catch(Exception err) {
		    model.addAttribute("error", err);
			return "ActionsViews/ActionsSupply";
		}
	}
	
	@PostMapping(path = "/Supply")
	public @ResponseBody String SupplyPost(@ModelAttribute Products product, Model model){
		try {
			
			Products productforSupply = productContext.findById(product.getid()).get();
			
			if(productforSupply == null) 
				throw new Exception();
				
			HelperMethods.Supply(product.getSupplier_Key(), productforSupply, product.getTransactionQuantity());
			
			
		}catch(Exception err) {
		    model.addAttribute("error", err);
			return "ActionsViews/ActionsSupply"; 
		}
		
		return "ActionsViews/ActionsSupply"; 
	}
	
	@GetMapping("/Display")
	public String DisplayGet(Model model) {
		try {
		    model.addAttribute("Products", productContext.findAll());
		    model.addAttribute("Product", new Products());
			return "ActionsViews/ActionsDisplay";
		}catch(Exception err) {
		    model.addAttribute("error", err);
			return "ActionsViews/ActionsDisplay";
		}
	}
	

	@PostMapping(path = "/Display")
	public @ResponseBody String DisplayPost(@ModelAttribute Products product, Model model){
		
		try {
			Products productForDisplay = productContext.findById(product.getid()).get();
			
			HelperMethods.Display(productForDisplay ,product.getTransactionQuantity(), product.getDepartment());
			return "ActionsViews/ActionsDisplay";
		}catch(Exception err) {
		    model.addAttribute("error", err);
			return "ActionsViews/ActionsDisplay";
		} 
	}	
		
	@GetMapping("/Buy")
	public String BuyGet(Model model) {
		try {
		    model.addAttribute("Products", productContext.findAll());
		    model.addAttribute("actionClass", new BuyActionClass());
			return "ActionsViews/ActionsBuy";
		}catch(Exception err) {
		    model.addAttribute("error", err);
			return "ActionsViews/ActionsBuy";
		}
	}
	
	@PostMapping(path = "/Buy")
	public @ResponseBody String BuyPost(@ModelAttribute BuyActionClass actionClass, Model model){

		try {
			List<Products> products = HelperMethods.BringAllProductsDepartments();
			HelperMethods.CheckValidity(actionClass);
			
			Products productBought = productContext.findById(actionClass.getProductKey()).get();
			
	        if (productBought == null)
	            throw new Exception("Major failure in server database");
	        
            if (productBought.getQuantityInDisplay() < actionClass.getQuantity())
                throw new Exception("Cannot buy that amount of product");
	        
            productBought.setTransactionQuantity(actionClass.getQuantity());
            
            Customers customer = customerContext.findById(actionClass.getCustomerKey()).get();
            
            if (customer == null)
                throw new Exception("Customer not found retry!");
            HelperMethods.UpdateProductInDisplay(productBought);
            HelperMethods.Buy(productBought, customer);
            return "ActionsViews/ActionsBuy";		
            
		}catch(Exception err) {
		    model.addAttribute("Exception", err);
			return "ActionsViews/ActionsBuy";		
		}
	}
}
