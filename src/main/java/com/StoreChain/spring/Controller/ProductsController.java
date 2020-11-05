package com.StoreChain.spring.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@GetMapping(path="/All")
	public @ResponseBody Iterable<Products> getAllProducts(){
		return productContext.findAll();
	}
	
	@PostMapping(path="/Create")
	public @ResponseBody String CreateNewProduct(
			@RequestParam int supplier,
			@RequestParam int category,
			@RequestParam int Department,
			@RequestParam Boolean isDisplay,
			@RequestParam double costSold,
			@RequestParam double costBought,
			@RequestParam int quantityStorage,
			@RequestParam int quantityDisplay,
			@RequestParam int departmentForeignId){
		
		Products newProduct = new Products();
		newProduct.setSupplier_Key(supplier);
		newProduct.setCategory(category);
		newProduct.setDepartment(Department);
		newProduct.setIsDisplay(isDisplay);
		newProduct.setCostBought(costBought);
		newProduct.setCostSold(costSold);
		newProduct.setQuantityInStorage(quantityStorage);
		newProduct.setQuantityInDisplay(quantityDisplay);
		newProduct.setDepartmentForeignId(departmentForeignId);
		//TODO check which department is not needed
		productContext.save(newProduct);
		
		return "Saved";
	}

	@PostMapping(path="/Updated")
	public @ResponseBody String UpdateProduct(
			@RequestParam int id,
			@RequestParam int supplier,
			@RequestParam int category,
			@RequestParam int Department,
			@RequestParam Boolean isDisplay,
			@RequestParam double costSold,
			@RequestParam double costBought,
			@RequestParam int quantityStorage,
			@RequestParam int quantityDisplay,
			@RequestParam int departmentForeignId){
		
		Optional<Products> updateToBe = productContext.findById(id);
		Products update = null;
		if(updateToBe.isPresent())
			update = updateToBe.get();
		
		update.setCategory(category);
		update.setDepartment(Department);
		update.setIsDisplay(isDisplay);
		update.setCostBought(costBought);
		update.setCostSold(costSold);
		update.setSupplier_Key(supplier);
		update.setQuantityInStorage(quantityStorage);
		update.setQuantityInDisplay(quantityDisplay);
		update.setDepartmentForeignId(departmentForeignId);
		//TODO check which department is not needed
		productContext.save(update);
		
		return "Updated";
	}
	
	@PostMapping(path="/Delete")
	public @ResponseBody String DeleteProduct(@RequestParam int id) {
		
		Optional<Products> toBeDeleted = productContext.findById(id);
		Products product = null;
		
		if(toBeDeleted.isPresent())
			product = toBeDeleted.get();
		
		productContext.delete(product);
		
		return "Deleted";
	}
}

