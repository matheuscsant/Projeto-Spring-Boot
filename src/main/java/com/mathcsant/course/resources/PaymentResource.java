package com.mathcsant.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mathcsant.course.entities.Payment;
import com.mathcsant.course.services.PaymentService;

// Identificar como camada de Resource
@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

	// Injeção de dependencia automatica
	@Autowired
	private PaymentService service;

	// Requisição do tipo GET
	@GetMapping
	public ResponseEntity<List<Payment>> findAll() {
//		List<User> list = service.findAll();
//		return ResponseEntity.ok().body(list);
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Payment> findById(@PathVariable Long id) {
		Payment p = service.findById(id);
		return ResponseEntity.ok().body(p);
//		return ResponseEntity.ok().body(service.findById(id));
	}

}
