package com.mathcsant.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathcsant.course.entities.OrderItem;
import com.mathcsant.course.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
