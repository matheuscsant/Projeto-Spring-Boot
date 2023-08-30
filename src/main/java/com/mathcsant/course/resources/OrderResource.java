package com.mathcsant.course.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mathcsant.course.entities.Order;
import com.mathcsant.course.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	OrderService service;

	@GetMapping
	public ResponseEntity<List<Order>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		Order ord = service.findById(id);
		return ResponseEntity.ok().body(ord);
	}

	@PostMapping
	public ResponseEntity<Object> createOrder(@Valid @RequestBody Order order, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}

		Order createdOrder = service.createOrder(order);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/orders/{id}")
				.buildAndExpand(createdOrder.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PostMapping("/create-orders")
	public ResponseEntity<Object> createOrders(@RequestBody List<Order> orders) {
		List<Order> createdOrders = service.createOrders(orders);
		List<URI> uris = new ArrayList<>();
		createdOrders.forEach(o -> uris.add(ServletUriComponentsBuilder.fromCurrentContextPath().path("/orders/{id}")
				.buildAndExpand(o.getId()).toUri()));
		return ResponseEntity.status(HttpStatus.CREATED).body(uris);
	}

	@PutMapping
	public ResponseEntity<Object> updateOrder(@RequestParam Long id, @Valid @RequestBody Order order,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		Order updatedOrder = service.updateOrder(id, order);
		return ResponseEntity.ok().body(updatedOrder);
	}
}
