package com.mathcsant.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathcsant.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findByName(String name);

	public Boolean existsByName(String name);

	public void deleteByName(String name);

}
