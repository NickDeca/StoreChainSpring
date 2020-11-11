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

import com.StoreChain.spring.Repository.SuppliersRepository;
import com.StoreChain.spring.model.Suppliers;

@Controller
@RequestMapping(path="/Suppliers")
public class SuppliersController {
	
	@Autowired 
	private SuppliersRepository SupplierContext;
	
	@GetMapping("*")
	public String Index() {
				
		return "productsIndex";
	}
	
	@GetMapping("/All")
	public @ResponseBody Iterable<Suppliers> getAllProducts(){
		return SupplierContext.findAll();
	}
	
	@GetMapping("/Create")
	public String CreateNewProductGet(Model model) {
	    model.addAttribute("Suppliers", new Suppliers());
		return "CreateProduct";
	}
	
	@PostMapping("/Create")
	public RedirectView CreateNewProduct(@ModelAttribute Suppliers supplier, Model model){
			
		SupplierContext.save(supplier);		
		return new RedirectView("CreateSuppliers"); 
	}
	
	@GetMapping("/Update")
	public String UpdateProductGet(Model model) {
	    model.addAttribute("Products", new Suppliers());
		return "UpdateSuppliers";
	}
	

	@PostMapping("/Update")
	public @ResponseBody String UpdateSuppliers(@ModelAttribute Suppliers supplier, Model model){
		
		Optional<Suppliers> updateToBe = SupplierContext.findById(supplier.getId());
		Suppliers update = null;
		if(updateToBe.isPresent())
			update = updateToBe.get();
		
		if(supplier.getPaymentDue() != null)
			update.setPaymentDue(supplier.getPaymentDue());			
		
		if(supplier.getCategory() != null)
			update.setCategory(supplier.getCategory());
		
		if(supplier.getDescription() != null)
		    update.setDescription(supplier.getDescription());	
		
		if(supplier.getName() != null)
			update.setName(supplier.getName());
						
		SupplierContext.save(update);
		
		return "Updated";
	}
	
	@GetMapping("/Delete")
	public @ResponseBody String DeleteSupplier(Model model) {
		model.addAttribute("Suppliers", new Suppliers());
		return "DeleteSuppliers";
	}
	

	@PostMapping("/Delete")
	public @ResponseBody String DeleteSupplier(@RequestParam int id) {
		Optional<Suppliers> toBeDeleted = SupplierContext.findById(id);
		Suppliers supplier = null;
		
		if(toBeDeleted.isPresent())
			supplier = toBeDeleted.get();
		
		SupplierContext.delete(supplier);
		
		return "Deleted";
	}
}
