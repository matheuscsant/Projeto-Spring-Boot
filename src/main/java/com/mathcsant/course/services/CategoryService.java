package com.mathcsant.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.Category;
import com.mathcsant.course.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repository;

	@Autowired
	private PagingAndSortingRepository<Category, Long> repo;

	public List<Category> findAll() {
//		Long count = repository.count();
//		System.out.println(count);

		List<Category> testePag = repo.findAll(PageRequest.of(0, 2)).toList();
		List<Category> testePag2 = repo.findAll(PageRequest.of(1, 2)).toList();
//		List<Category> testePag3 = repo.findAll(PageRequest.of(2, 1)).toList();

//		System.out.print("\033[H\033[2J");
//		System.out.flush();

//		testePag.stream().filter(i -> i.getName() == i.getId()).toList().forEach(null);
		testePag.forEach(System.out::println);
		testePag2.forEach(System.out::println);
//		testePag3.forEach(System.out::println);

		return repository.findAll();
	}

	public Category findById(Long id) {
		Optional<Category> c = repository.findById(id);
		boolean boo = repository.existsById(id);
		System.out.println(boo);
		return c.get();
	}

	public List<Category> findCategoriesByName(String name) {
		return repository.findByName(name);
	}

}
