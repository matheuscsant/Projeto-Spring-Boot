package com.mathcsant.course.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.Product;
import com.mathcsant.course.repositories.CategoryRepository;
import com.mathcsant.course.repositories.ProductRepository;
import com.mathcsant.course.services.exceptions.DataBaseException;
import com.mathcsant.course.services.exceptions.ResourceNotFoundException;
import com.mathcsant.course.utils.entities.updateEntities;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		Optional<Product> p = productRepository.findById(id);
		return p.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Product createProduct(Product product) {
		validateCategory(product);
		return productRepository.save(product);
	}

	public List<Product> createProducts(List<Product> products) {
		products.forEach(p -> validateCategory(p));
		List<Product> createdProducts = new ArrayList<>();
		products.forEach(p -> createdProducts.add(productRepository.save(p)));
		return createdProducts;
	}

	public Product updateProduct(Long id, Product updatedProduct) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		validateCategory(updatedProduct);
		Product existingProduct = productRepository.getReferenceById(id);
		updateEntities.updateProduct(updatedProduct, existingProduct);
		return productRepository.save(existingProduct);
	}

	public void deleteProductById(Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		try {
			productRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	private void validateCategory(Product product) {
		if (!product.getCategories().isEmpty()) {
			product.getCategories().forEach(c -> {
				if (!categoryRepository.existsById(c.getId()) || 
						!categoryRepository.getReferenceById(c.getId()).getName().equalsIgnoreCase(c.getName())) {
					throw new ResourceNotFoundException(c.getId());
				}
			});
		}
	}

}
