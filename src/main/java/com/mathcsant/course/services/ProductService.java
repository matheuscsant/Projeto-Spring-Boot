package com.mathcsant.course.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.Category;
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

	public Set<Category> findCategoriesAssociatedProduct(Long id) {
		Optional<Product> p = productRepository.findById(id);
		return p.orElseThrow(() -> new ResourceNotFoundException(id)).getCategories();
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

	public Product associateCategoryProduct(Long id, List<Long> categoriesIds) {
		Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		categoriesIds.forEach(i -> {
			if (!categoryRepository.existsById(i)) {
				throw new ResourceNotFoundException(i);
			}
		});
		List<Category> categories = categoryRepository.findAllById(categoriesIds);
		existingProduct.getCategories().addAll(categories);
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

	public Product disassociateCategoryProduct(Long productId, Long categoryId) {
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(productId));
		Category existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(categoryId));
		if (!existingProduct.getCategories().contains(existingCategory)) {
			throw new ResourceNotFoundException(categoryId);
		}
		existingProduct.getCategories().removeIf(c -> c.equals(existingCategory));
		return productRepository.save(existingProduct);
	}

	private void validateCategory(Product product) {
		if (!product.getCategories().isEmpty()) {
			product.getCategories().forEach(c -> {
				if (!categoryRepository.existsById(c.getId())) {
					throw new ResourceNotFoundException(c.getId());
				}
			});
		}
	}

}
