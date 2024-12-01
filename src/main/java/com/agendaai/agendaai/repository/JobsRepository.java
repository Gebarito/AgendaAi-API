package com.agendaai.agendaai.repository;

import com.agendaai.agendaai.model.Jobs;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, UUID>{

    List<Jobs> findAllByActiveOrderByDateCreated(boolean active, Pageable pageable);
    List<Jobs> findAllByCategoryAndActive(String category, boolean active);
    
}
