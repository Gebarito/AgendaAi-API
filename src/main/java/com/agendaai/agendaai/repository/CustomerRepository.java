package com.agendaai.agendaai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendaai.agendaai.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

    Optional<Customer> findByCpf(String cpf);

}
