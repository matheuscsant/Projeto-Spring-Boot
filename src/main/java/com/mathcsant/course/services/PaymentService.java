package com.mathcsant.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathcsant.course.entities.Payment;
import com.mathcsant.course.repositories.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository repository;

	public List<Payment> findAll() {
		return repository.findAll();
	}

	public Payment findById(Long Id) {
		Optional<Payment> obj = repository.findById(Id);
		return obj.get();
	}

}
