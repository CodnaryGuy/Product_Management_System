package com.codna.apis.msystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codna.apis.msystem.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
