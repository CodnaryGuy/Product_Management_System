package com.codna.apis.msystem.service;

import java.util.List;

import com.codna.apis.msystem.dto.ProductDto;
import com.codna.apis.msystem.model.Product;

public interface ProductService {

	public Boolean saveProduct(ProductDto productDto);
	
	public List<ProductDto> getAllProducts();
	
	public ProductDto getProductById(Integer id);
	
	public Boolean deleteProduct(Integer id);
}
