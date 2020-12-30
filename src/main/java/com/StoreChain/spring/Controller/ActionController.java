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
import org.springframework.web.servlet.view.RedirectView;

import com.StoreChain.spring.Helper.BuyActionClass;
import com.StoreChain.spring.Helper.HelperMethods;
import com.StoreChain.spring.Model.Customers;
import com.StoreChain.spring.Model.Department;
import com.StoreChain.spring.Model.Products;
import com.StoreChain.spring.Repository.CustomersRepository;
import com.StoreChain.spring.Repository.DepartmentRepository;
import com.StoreChain.spring.Repository.ProductRepository;
import com.StoreChain.spring.Repository.StoreRepository;
import com.StoreChain.spring.Repository.SuppliersRepository;
import com.StoreChain.spring.Repository.TransactionsRepository;


@Controller
@RequestMapping(path="/Actions")
public class ActionController {
	
	@Autowired
	private ProductRepository productContext;	
	@Autowired
	private CustomersRepository customerContext;
	@Autowired
	private DepartmentRepository departmentContext;	
	@Autowired
	private TransactionsRepository transactionContext;
	@Autowired 
	private SuppliersRepository supplierContext;
	@Autowired
	private StoreRepository storeContext;

	@GetMapping("*")
	public String Index() {
		
		return "ActionsViews/ActionsIndex";
	}
	
	@GetMapping("/Supply")
	public String SupplyGet(Model model) {
		try {
		    model.addAttribute("Product", new Products());
			return "ActionsViews/ActionsSupply";
		}catch(Exception err) {
		    model.addAttribute("error", err);
			return "ActionsViews/ActionsSupply";
		}
	}
	
	@PostMapping(path = "/Supply")
	public RedirectView  SupplyPost(@ModelAttribute Products product, Model model){
		try {
			
			Products productforSupply = productContext.findById(product.getid()).get();
			HelperMethods helperMethods = new HelperMethods(productContext,customerContext,departmentContext,transactionContext, supplierContext, storeContext);
			
			if(productforSupply == null) 
				throw new Exception();
				
			helperMethods.Supply(product.getSupplier_Key(), productforSupply, product.getTransactionQuantity());

		    model.addAttribute("Product", new Products());
			return new RedirectView("/Actions/Supply"); 	
			
		}catch(Exception err) {
		    model.addAttribute("Product", new Products());
		    model.addAttribute("error", err);
			return new RedirectView("/Actions/Supply"); 	
		}
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
	public RedirectView DisplayPost(@ModelAttribute Products product, Model model){
		
		try {			
			HelperMethods helperMethods = new HelperMethods(productContext,customerContext,departmentContext,transactionContext, supplierContext, storeContext);
			helperMethods.Display(product.getid() ,product.getTransactionQuantity(), product.getDepartment());
		    model.addAttribute("Products", productContext.findAll());
		    model.addAttribute("Product", new Products());
			return new RedirectView("/Actions/Display");
		}catch(Exception err) {
		    model.addAttribute("error", err);
		    model.addAttribute("Products", productContext.findAll());
		    model.addAttribute("Product", new Products());
		    return new RedirectView("/Actions/Display");
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
	public RedirectView BuyPost(@ModelAttribute BuyActionClass actionClass, Model model){

		try {
			HelperMethods helperMethods = new HelperMethods(productContext,customerContext,departmentContext,transactionContext, supplierContext, storeContext);
			helperMethods.CheckValidity(actionClass);
			
			Products productBought = productContext.findById(actionClass.getProductKey()).get();
			
	        if (productBought == null)
	            throw new Exception("Major failure in server database");
	        
            if (productBought.getQuantityInDisplay() < actionClass.getQuantity())
                throw new Exception("Cannot buy that amount of product");
	        
            productBought.setTransactionQuantity(actionClass.getQuantity());
            
            Customers customer = customerContext.findById(actionClass.getCustomerKey()).get();
            
            if (customer == null)
                throw new Exception("Customer not found retry!");
            helperMethods.UpdateProductInDisplay(productBought);
            helperMethods.Buy(productBought, customer);

		    model.addAttribute("Products", productContext.findAll());
		    model.addAttribute("actionClass", new BuyActionClass());
		    return new RedirectView("/Actions/Buy");
            
		}catch(Exception err) {
		    model.addAttribute("Exception", err);

		    model.addAttribute("Products", productContext.findAll());
		    model.addAttribute("actionClass", new BuyActionClass());
		    return new RedirectView("/Actions/Buy");
		}
	}
	
	@GetMapping("/Transactions")
	public String TransactionsGet(Model model) {
		try {
		    model.addAttribute("Transactions", transactionContext.getLastTenTransactions());
			return "ActionsViews/ActionsTransactions";
		}catch(Exception err) {
		    model.addAttribute("error", err);
		    model.addAttribute("Transactions", transactionContext.getLastTenTransactions());
			return "ActionsViews/ActionsTransactions";
		}
	}
}
