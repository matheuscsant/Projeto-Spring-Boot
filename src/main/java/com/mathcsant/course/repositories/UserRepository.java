package com.mathcsant.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathcsant.course.entities.User;

// Não é necessário utilizar a anotação @Repository, pois a própria interface JpaRepository
// registra esta classe como um componente Spring
public interface UserRepository extends JpaRepository<User, Long> {

}
