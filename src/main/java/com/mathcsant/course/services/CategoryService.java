package com.mathcsant.course.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.Category;
import com.mathcsant.course.repositories.CategoryRepository;
import com.mathcsant.course.services.exceptions.DataBaseException;
import com.mathcsant.course.services.exceptions.ResourceNotFoundException;
import com.mathcsant.course.utils.entities.updateEntities;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public List<Category> findAll() {
		return repository.findAll();
	}

	public Category findById(Long id) {
		Optional<Category> c = repository.findById(id);
		return c.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Category findByName(String name) {
		if (!repository.existsByName(name)) {
			throw new ResourceNotFoundException(name);
		}
		return repository.findByName(name);
	}

	public Category createCategory(Category category) {
		Category createdCategory = null;
		try {
			createdCategory = repository.save(category);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
		return createdCategory;
	}

	public List<Category> createCategories(List<Category> categories) {
		List<Category> createdCategories = new ArrayList<>();
		categories.forEach(c -> createdCategories.add(repository.save(c)));
		return createdCategories;
	}

	public Category uptadeCategory(Long id, Category updatedCategory) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		Category existingCategory = repository.getReferenceById(id);
		updateEntities.updateCategory(updatedCategory, existingCategory);
		return repository.save(existingCategory);
	}

	public void deleteById(Long id) {
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
