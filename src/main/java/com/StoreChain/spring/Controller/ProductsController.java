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

import com.StoreChain.spring.Model.ProductMinQuantity;
import com.StoreChain.spring.Model.Products;
import com.StoreChain.spring.Repository.ProductMinQuantityRepository;
import com.StoreChain.spring.Repository.ProductRepository;


@Controller
@RequestMapping(path="/Products")
public class ProductsController {
	
	@Autowired 
	private ProductRepository productContext;
	@Autowired
	private ProductMinQuantityRepository pmContext;
	
	@GetMapping("*")
	public String Index() {
				
		return "ProductsViews/productsIndex";
	}
	
	@GetMapping("/All")
	public @ResponseBody Iterable<Products> getAllProducts(){
		return productContext.findAll();
	}
	
	@GetMapping("/Create")
	public String CreateNewProductGet(Model model) {
	    model.addAttribute("Products", new Products());
		return "ProductsViews/CreateProduct";
	}
	
	@PostMapping(path = "/Create")
	public String CreateNewProduct(@ModelAttribute Products product, Model model){
			
		product.setTransactionQuantity(0);
		productContext.save(product);
		
		pmContext.save(new ProductMinQuantity(product.getid(), product.getMaxDisplay(), product.getMinStorage()));
	    model.addAttribute("Products", new Products());
		return "ProductsViews/CreateProduct"; 
	}
	
	@GetMapping("/Update")
	public String UpdateProductGet(Model model) {
	    model.addAttribute("Products", new Products());
		return "ProductsViews/UpdateProduct";
	}

	@PostMapping("/Update")
	public String UpdateProduct(@ModelAttribute Products product, Model model){
		
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
				
		if(product.getQuantityInDisplay() != null)
			update.setQuantityInDisplay(product.getQuantityInDisplay());
		
		if(product.getQuantityInStorage() != null)
			update.setQuantityInStorage(product.getQuantityInStorage());
		
		if(product.getSupplier_Key() != null)
			update.setSupplier_Key(product.getSupplier_Key());
		
		if(product.getMinStorage() != null)
			update.setMinStorage(product.getMinStorage());
		
		if(product.getMaxDisplay() != null)
			update.setMaxDisplay(product.getMaxDisplay());
		
		productContext.save(update);
		
	    model.addAttribute("Products", new Products());
		
		return "ProductsViews/UpdateProduct";
	}
	
	@GetMapping("/Delete")
	public String DeleteProductGet(Model model) {
	    model.addAttribute("Products", new Products());
		return "ProductsViews/DeleteProduct";
	}
	
	@PostMapping("/Delete")
	public String DeleteProduct(@RequestParam int id, Model model) {
		
		Optional<Products> toBeDeleted = productContext.findById(id);
		Products product = null;
		
		if(toBeDeleted.isPresent())
			product = toBeDeleted.get();
		
		productContext.delete(product);

	    model.addAttribute("Products", new Products());
		return "ProductsViews/DeleteProduct";
	}
}

