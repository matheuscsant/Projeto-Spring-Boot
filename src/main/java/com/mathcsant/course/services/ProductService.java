package com.mathcsant.course.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.Product;
import com.mathcsant.course.repositories.ProductRepository;
import com.mathcsant.course.services.exceptions.DataBaseException;
import com.mathcsant.course.services.exceptions.ResourceNotFoundException;

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

	public Product createProduct(Product product) {
		return repository.save(product);
	}

	public List<Product> createProducts(List<Product> products) {
		List<Product> createdProducts = new ArrayList<>();
		products.forEach(p -> createdProducts.add(repository.save(p)));
		return createdProducts;
	}

	public Product updateProduct(Long id, Product updatedProduct) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		Product existingProduct = repository.getReferenceById(id);

		updateProduct(updatedProduct, existingProduct);

		return repository.save(existingProduct);
	}

	private void updateProduct(Product updatedProduct, Product existingProduct) {
		existingProduct.setName(updatedProduct.getName());
		existingProduct.setDescription(updatedProduct.getDescription());
		existingProduct.setPrice(updatedProduct.getPrice());
		existingProduct.setImgUrl(updatedProduct.getImgUrl());
	}

	public void deleteProductById(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

}
