package com.codna.apis.msystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Integer quantity;
	
}
