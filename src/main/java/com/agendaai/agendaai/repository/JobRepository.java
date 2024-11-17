package com.agendaai.agendaai.repository;

import com.agendaai.agendaai.model.Job;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID>{

    List<Job> findAllByOrderByDateCreated(Pageable pageable);

    List<Job> findAllByCategory(String category);
    
}
