package com.codna.apis.msystem.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codna.apis.msystem.dto.ProductDto;
import com.codna.apis.msystem.model.Product;
import com.codna.apis.msystem.repository.ProductRepository;
import com.codna.apis.msystem.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	@Override
	public Boolean saveProduct(ProductDto productDto) {
		
		Product product = new Product();
		product.setId(productDto.getId());
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setQuantity(productDto.getQuantity());
		Product save = productRepository.save(product);
		
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}

	@Override
	public List<ProductDto> getAllProducts() {

		List<Product> products = productRepository.findAll();
		List<ProductDto> productDtoList = products.stream()
		.map(product -> new ProductDto(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getQuantity()
			))
		.collect(Collectors.toList());
		
		return productDtoList;
	}

	@Override
	public ProductDto getProductById(Integer id) {

		Optional<Product> findByProduct = productRepository.findById(id);
		if(findByProduct.isPresent()) {
			ProductDto productDto = findByProduct.map(product -> new ProductDto(
					product.getId(),
					product.getName(),
					product.getDescription(),
					product.getPrice(),
					product.getQuantity()
				)).orElseThrow();
			
			return productDto;
		}
		return null;
	}

	@Override
	public Boolean deleteProduct(Integer id) {
		
		Optional<Product> findByProduct = productRepository.findById(id);
		if(findByProduct.isPresent()) {
			Product product = findByProduct.get();
			productRepository.delete(product);
			return true;
		}
		return false;
	}

}
