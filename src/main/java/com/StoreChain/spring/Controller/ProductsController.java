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

import com.StoreChain.spring.Repository.ProductRepository;
import com.StoreChain.spring.model.Products;


@Controller
@RequestMapping(path="/Products")
public class ProductsController {
	
	@Autowired 
	private ProductRepository productContext;
	
	@GetMapping("/ProdIndex")
	public String Index() {
				
		return "productsIndex";
	}
	
	@GetMapping("/All")
	public @ResponseBody Iterable<Products> getAllProducts(){
		return productContext.findAll();
	}
	
	@GetMapping("/Create")
	public String CreateNewProductGet(Model model) {
	    model.addAttribute("Products", new Products());
		return "CreateProduct";
	}
	
	@PostMapping(path = "/Create")
	public RedirectView CreateNewProduct(@ModelAttribute Products product, Model model){
			
		productContext.save(product);		
		return new RedirectView("CreateProduct"); 
	}
	
	@GetMapping("/Update")
	public String UpdateProductGet(Model model) {
	    model.addAttribute("Products", new Products());
		return "UpdateProduct";
	}

	@PostMapping("/Update")
	public @ResponseBody String UpdateProduct(@ModelAttribute Products product, Model model){
		
		Optional<Products> updateToBe = productContext.findById(product.getid());
		Products update = null;
		if(updateToBe.isPresent())
			update = updateToBe.get();
		
		if(product.getCategory() != null)
			update.setCategory(product.getCategory());
		
		if(product.getCostBought() != null)
			update.setCostBought(product.getCostBought());
		
		if(product.getCostSold() != null)
			update.setCostSold(product.getCostSold());
		
		if(product.getDepartment() != null)
			update.setDepartment(product.getDepartment());
		
		if(product.getDepartmentForeignId() != null)
			update.setDepartmentForeignId(product.getDepartmentForeignId());
		
		if(product.getDescription() != null)
			update.setDescription(product.getDescription());
		
		if(product.getIsDisplay() != null)
			update.setIsDisplay(product.getIsDisplay());
		
		if(product.getQuantityInDisplay() != null)
			update.setQuantityInDisplay(product.getQuantityInDisplay());
		
		if(product.getQuantityInStorage() != null)
			update.setQuantityInStorage(product.getQuantityInStorage());
		
		if(product.getSupplier_Key() != null)
			update.setSupplier_Key(product.getSupplier_Key());
		
		//TODO check which department is not needed
		productContext.save(update);
		
		return "Updated";
	}
	
	@GetMapping("/Delete")
	public String DeleteProductGet(Model model) {
	    model.addAttribute("Products", new Products());
		return "DeleteProduct";
	}
	
	@PostMapping("/Delete")
	public @ResponseBody String DeleteProduct(@RequestParam int id) {
		
		Optional<Products> toBeDeleted = productContext.findById(id);
		Products product = null;
		
		if(toBeDeleted.isPresent())
			product = toBeDeleted.get();
		
		productContext.delete(product);
		
		return "Deleted";
	}
}

