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

import com.mathcsant.course.entities.User;
import com.mathcsant.course.services.UserService;

import jakarta.validation.Valid;

// Identificar como camada de Resource
@RestController
@RequestMapping(value = "/users")
public class UserResource {

	// Injeção de dependencia automatica
	@Autowired
	private UserService service;

	// Requisição do tipo GET
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
//		List<User> list = service.findAll();
//		return ResponseEntity.ok().body(list);
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User u = service.findById(id);
		return ResponseEntity.ok().body(u);
//		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user, BindingResult result) {

		if (result.hasErrors()) {
			// Retornar erros de validação
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}

		// Cria novo usuário
//		User createdUser = service.createUser(user);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

		User createdUser = service.createUser(user);

		// Outra forma de retornar 201 (Aqui retornar o caminho no header)
		// fromCurrentRequest() -> Pega o caminho em que já estamos
		// fromCurrentContextPath() -> Pega o caminho que colocarmos
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("users/{id}")
				.buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(uri).body(createdUser);
	}

	@PostMapping(value = "/create-users")
	public ResponseEntity<List<?>> createUsers(@Valid @RequestBody List<User> users) {
		// TODO Por ser lista deve ser feita uma validação de forma diferente
		// Operações com apenas um dado pode se usar o Binding Result para controlar as
		// validações

		// Cria novos usuários
		List<User> createdUsers = service.createUsers(users);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable Long id, @Valid @RequestBody User user,
			BindingResult result) {

		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		User updatedUser = service.updateUser(id, user);

		return updatedUser != null ? ResponseEntity.ok().body(updatedUser) : ResponseEntity.notFound().build();
		// : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
		// EntityNotFoundException("Entity not found").getMessage());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//		return service.deleteUserById(id) > 0 ? ResponseEntity.ok().body("Successfully deleted user")
//				: ResponseEntity.badRequest().body("Deletion failed");
		service.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}

}
