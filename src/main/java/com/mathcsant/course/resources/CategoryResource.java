package com.mathcsant.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mathcsant.course.entities.Category;
import com.mathcsant.course.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
//		List<Category> list = service.findAll();
//		return ResponseEntity.ok().body(list);
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category c = service.findById(id);
		if (c != null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(c);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<List<Category>> findByName(@RequestParam(name = "name") String name) {
		List<Category> c = service.findCategoriesByName(name);
		return ResponseEntity.ok().body(c);
	}
}
