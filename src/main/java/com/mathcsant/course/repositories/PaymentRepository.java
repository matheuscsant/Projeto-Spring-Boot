package com.mathcsant.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathcsant.course.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
