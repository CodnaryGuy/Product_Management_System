package com.codna.apis.msystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductResponse {
	
	private List<ProductDto> products;
	private long totalElements;
	private int totalPages;
	private Boolean isFirst;
	private Boolean isLast;
	private int pageNumber;
	private int pageSize;
}
