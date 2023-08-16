package com.mathcsant.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.OrderItem;
import com.mathcsant.course.entities.pk.OrderItemPK;
import com.mathcsant.course.repositories.OrderItemRepository;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository repository;

	public List<OrderItem> findAll() {
		return repository.findAll();
	}

	public OrderItem findById(OrderItemPK id) {
		return repository.findById(id).orElse(null);

	}

}
