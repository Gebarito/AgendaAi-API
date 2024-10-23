package com.agendaai.agendaai.repositories;

import com.agendaai.agendaai.models.ProductModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long>{
    
}
