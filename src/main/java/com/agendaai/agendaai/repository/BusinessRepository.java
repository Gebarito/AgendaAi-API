package com.agendaai.agendaai.repository;

import com.agendaai.agendaai.model.Business;
import com.agendaai.agendaai.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long>{

    Business findByCnpj(String cnpj);
    Business findJobsById(Long id);
}
