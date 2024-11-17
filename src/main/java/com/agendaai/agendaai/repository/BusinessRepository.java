package com.agendaai.agendaai.repository;

import com.agendaai.agendaai.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long>{

    Optional<Business> findByCnpj(String cnpj);

}
