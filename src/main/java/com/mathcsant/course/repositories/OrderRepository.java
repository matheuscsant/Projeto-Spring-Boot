package com.mathcsant.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathcsant.course.entities.Order;

// Não é necessário adicionar a anotação @Repository, pois a interface já registra
// a classe como um repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
