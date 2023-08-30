package com.mathcsant.course.resources;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mathcsant.course.entities.Category;
import com.mathcsant.course.entities.Product;
import com.mathcsant.course.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService service;

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@GetMapping(value = "/{id}/categories")
	public ResponseEntity<Set<Category>> findCategoriesAssociatedProduct(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findCategoriesAssociatedProduct(id));
	}

	@PostMapping
	public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product, BindingResult result) {

		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}

		Product createdProduct = service.createProduct(product);

		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("products/{id}")
				.buildAndExpand(createdProduct.getId()).toUri();

		return ResponseEntity.created(uri).body(createdProduct);
	}

	@PostMapping(value = "/create-products")
	public ResponseEntity<List<?>> createProducts(@RequestBody List<Product> products) {
		List<Product> createdProducts = service.createProducts(products);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProducts);
	}

	@PostMapping(value = "/{productId}/categories")
	public ResponseEntity<Object> associateCategoryProduct(@PathVariable Long productId,
			@RequestBody List<Long> categoriesIds) {
		Product updatedProduct = service.associateCategoryProduct(productId, categoriesIds);
		return ResponseEntity.ok().body(updatedProduct);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		Product updatedProduct = service.updateProduct(id, product);
		return ResponseEntity.ok().body(updatedProduct);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
		service.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{productId}/categories/{categoryId}")
	public ResponseEntity<Product> disassociateCategoryProduct(@PathVariable Long productId,
			@PathVariable Long categoryId) {
		Product p = service.disassociateCategoryProduct(productId, categoryId);
		return ResponseEntity.ok().body(p);
	}

}
