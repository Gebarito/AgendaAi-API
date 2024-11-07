package com.agendaai.agendaai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendaai.agendaai.model.Job;
import com.agendaai.agendaai.repository.JobRepository;

import jakarta.transaction.Transactional;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Transactional
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(UUID id) {
        return jobRepository
            .findById(id)
            .orElse(null);
    }

    @Transactional
    public Job updateJob(UUID jobId, Job job) {
        Job existingJob = getJobById(jobId);

        if (existingJob != null) {
            existingJob.setName(job.getName());
            existingJob.setAmount(job.getAmount());
            existingJob.setDescription(job.getDescription());

            return jobRepository.save(existingJob);
        }

        return null;
    }

    public void deleteJob(UUID id) {
        jobRepository.deleteById(id);
    }

}
