package com.mathcsant.course.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathcsant.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	public List<Category> findByName(String name);

}