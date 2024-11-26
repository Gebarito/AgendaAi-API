package com.agendaai.agendaai.repository;

import com.agendaai.agendaai.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long>{

    Customers findByCpf(String cpf);
    Customers findOrdersById(Long id);
}
