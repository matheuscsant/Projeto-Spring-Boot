package com.mathcsant.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.Product;
import com.mathcsant.course.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<Product> findAll() {
		return repository.findAll();
	}

	public Product findById(Long id) {
//		Optional<Product> p = repository.findById(id);
//		return p.get();
		return repository.findById(id).orElse(null);
	}

}
