package com.codna.apis.msystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codna.apis.msystem.dto.ProductDto;
import com.codna.apis.msystem.service.ProductService;

@RestController
public class ProductController {

	private ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> getProdut(){
		
		List<ProductDto> allProducts = new ArrayList<>();
		try {
			allProducts = productService.getAllProducts();
			if(CollectionUtils.isEmpty(allProducts)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProduts(@PathVariable(name = "id") Integer id){
		
		ProductDto productDto = null;
		try {
			productDto = productService.getProductById(id);
			if(ObjectUtils.isEmpty(productDto)) {
				return new ResponseEntity<>("Product not found with id="+id, HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}

	
	@PostMapping("/save-product")
	public ResponseEntity<?> saveProdut(@RequestBody ProductDto productDto){
		
		try {
			Boolean saveProduct = productService.saveProduct(productDto);
			if(!saveProduct) {
				return new ResponseEntity<>("product not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("saved success", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer id){
		
		Boolean deleteProduct = null;
		try {
			deleteProduct = productService.deleteProduct(id);
			if(!deleteProduct) {
				return new ResponseEntity<>("Product not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("Delete Success", HttpStatus.OK);
	}
}
