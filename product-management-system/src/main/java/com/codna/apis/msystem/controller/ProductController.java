package com.codna.apis.msystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
