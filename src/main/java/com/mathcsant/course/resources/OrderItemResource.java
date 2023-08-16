package com.mathcsant.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mathcsant.course.entities.OrderItem;
import com.mathcsant.course.entities.pk.OrderItemPK;
import com.mathcsant.course.services.OrderItemService;
import com.mathcsant.course.services.OrderService;
import com.mathcsant.course.services.ProductService;

@RestController
@RequestMapping(value = "/order-itens")
public class OrderItemResource {

	@Autowired
	private OrderItemService service;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<OrderItem>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/search")
	public ResponseEntity<OrderItem> findById(@RequestParam(name = "id_prod") Long id_prod,
			@RequestParam(name = "id_ord") Long id_ord) {
		OrderItemPK ordPk = new OrderItemPK();
		ordPk.setOrder(orderService.findById(id_ord));
		ordPk.setProduct(productService.findById(id_prod));
		if (service.findById(ordPk) != null) {
			return ResponseEntity.ok().body(service.findById(ordPk));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
