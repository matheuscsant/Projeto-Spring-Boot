package com.mathcsant.course.resources;

import java.net.URI;
import java.util.List;

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
import com.mathcsant.course.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@GetMapping(value = "/search-by-name/{name}")
	public ResponseEntity<Category> findByName(@PathVariable String name) {
		return ResponseEntity.ok().body(service.findByName(name));
	}

	@PostMapping
	public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category, BindingResult result) {

		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}

		Category createdCategory = service.createCategory(category);

		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("product/{id}")
				.buildAndExpand(createdCategory.getId()).toUri();
		return ResponseEntity.created(uri).body(createdCategory);
	}

	@PostMapping(value = "/create-categories")
	public ResponseEntity<List<Category>> createCategories(@Valid @RequestBody List<Category> categories) {
		List<Category> createdCategories = service.createCategories(categories);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategories);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		Category updatedCategory = service.uptadeCategory(id, category);
		return ResponseEntity.ok().body(updatedCategory);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
