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

import com.StoreChain.spring.Model.Suppliers;
import com.StoreChain.spring.Repository.SuppliersRepository;

@Controller
@RequestMapping(path="/Suppliers")
public class SuppliersController {
	
	@Autowired 
	private SuppliersRepository SupplierContext;
	
	@GetMapping("*")
	public String Index() {
				
		return "SuppliersViews/SuppliersIndex";
	}
	
	@GetMapping("/All")
	public @ResponseBody Iterable<Suppliers> getAllProducts(){
		return SupplierContext.findAll();
	}
	
	@GetMapping("/Create")
	public String CreateNewProductGet(Model model) {
	    model.addAttribute("Suppliers", new Suppliers());
		return "SuppliersViews/CreateSuppliers";
	}
	
	@PostMapping("/Create")
	public String CreateNewProduct(@ModelAttribute Suppliers supplier, Model model){
			
		SupplierContext.save(supplier);		
	    model.addAttribute("Suppliers", new Suppliers());
		return "SuppliersViews/CreateSuppliers"; 
	}
	
	@GetMapping("/Update")
	public String UpdateProductGet(Model model) {
	    model.addAttribute("Suppliers", new Suppliers());
		return "SuppliersViews/UpdateSuppliers";
	}
	

	@PostMapping("/Update")
	public String UpdateSuppliers(@ModelAttribute Suppliers supplier, Model model){
		
		Optional<Suppliers> updateToBe = SupplierContext.findById(supplier.getId());
		Suppliers update = null;
		if(updateToBe.isPresent())
			update = updateToBe.get();
		
		if(supplier.getPayment_Due() != null)
			update.setPayment_Due(supplier.getPayment_Due());			
		
		if(supplier.getCategory() != null)
			update.setCategory(supplier.getCategory());
		
		if(supplier.getDescription() != null)
		    update.setDescription(supplier.getDescription());	
		
		if(supplier.getName() != null)
			update.setName(supplier.getName());
						
		SupplierContext.save(update);

	    model.addAttribute("Suppliers", new Suppliers());
		return "SuppliersViews/UpdateSuppliers";
	}
	
	@GetMapping("/Delete")
	public String DeleteSupplierGet(Model model) {
		model.addAttribute("Suppliers", new Suppliers());
		return "SuppliersViews/DeleteSuppliers";
	}
	

	@PostMapping("/Delete")
	public String DeleteSupplier(@RequestParam int id, Model model) {
		Optional<Suppliers> toBeDeleted = SupplierContext.findById(id);
		Suppliers supplier = null;
		
		if(toBeDeleted.isPresent())
			supplier = toBeDeleted.get();
		
		SupplierContext.delete(supplier);

		model.addAttribute("Suppliers", new Suppliers());
		return "SuppliersViews/DeleteSuppliers";
	}
}
