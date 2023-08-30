package com.mathcsant.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.Order;
import com.mathcsant.course.repositories.OrderRepository;
import com.mathcsant.course.repositories.UserRepository;
import com.mathcsant.course.services.exceptions.DataBaseException;
import com.mathcsant.course.services.exceptions.ResourceNotFoundException;
import com.mathcsant.course.utils.entities.updateEntities;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Order findById(Long id) {
		Order o = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return o;
	}

	public Order createOrder(Order order) {
		validateUserInOrder(order);
		return orderRepository.save(order);
	}

	public List<Order> createOrders(List<Order> orders) {
		// Proposta melhoria, criar um Map ou Array e armazenar as orders que der errado
		// e voltar tudo de uma vez
		orders.forEach(o -> validateUserInOrder(o));
		return orderRepository.saveAll(orders);
	}

	public Order updateOrder(Long id, Order updatedOrder) {
		validateUserInOrder(updatedOrder);
		if (!orderRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		Order existingOrder = orderRepository.getReferenceById(id);
		updateEntities.updateOrder(updatedOrder, existingOrder);
		return existingOrder;
	}

	private void validateUserInOrder(Order order) {
		if (order.getClient() == null) {
			throw new DataBaseException("Client in order: " + order.toString() + " is null");
		} else if (!userRepository.existsById(order.getClient().getId())) {
			throw new ResourceNotFoundException(order.getClient().getId());
		}
	}

}
