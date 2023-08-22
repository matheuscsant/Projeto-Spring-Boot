package com.mathcsant.course.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.User;
import com.mathcsant.course.repositories.UserRepository;
import com.mathcsant.course.services.exceptions.DataBaseException;
import com.mathcsant.course.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long Id) {
		Optional<User> obj = repository.findById(Id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(Id));
	}

	public User createUser(User user) {
		return repository.save(user);
	}

	public List<User> createUsers(List<User> users) {
		List<User> createdUsers = new ArrayList<>();
		users.forEach(u -> createdUsers.add(repository.save(u)));
		return createdUsers;
	}

	public User updateUser(Long id, User updatedUser) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		User existingUser = repository.getReferenceById(id);

		updateUser(updatedUser, existingUser);

		return repository.save(existingUser);
	}

	private void updateUser(User updatedUser, User existingUser) {
		existingUser.setName(updatedUser.getName());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setPhone(updatedUser.getPhone());
	}

	public void deleteUserById(Long id) {

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
