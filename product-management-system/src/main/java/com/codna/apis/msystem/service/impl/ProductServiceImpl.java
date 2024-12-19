package com.codna.apis.msystem.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codna.apis.msystem.dto.ProductDto;
import com.codna.apis.msystem.dto.ProductResponse;
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

	//Method to implement Pagination
	@Override
	public ProductResponse getProductsWithPagination(int pageNumber, int pageSize, String sortBy, String sortDirection) {

//		Sort ascending = Sort.by(sortBy).ascending();
//		Sort descending = Sort.by(sortBy).descending();
		
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findAll(pageable);
		
		List<Product> products = page.getContent();
		List<ProductDto> productDtos = products.stream().map(product -> new ProductDto(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getQuantity()
			)).toList();
		
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		boolean first = page.isFirst();
		boolean last = page.isLast();
		
		ProductResponse productResponse = ProductResponse.builder().products(productDtos)
										.totalElements(totalElements).totalPages(totalPages)
										.isFirst(first).isLast(last).pageNumber(pageNumber)
										.pageSize(pageSize).build();
		
		return productResponse;
	}

}
