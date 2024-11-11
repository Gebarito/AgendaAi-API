package com.agendaai.agendaai.service;

import java.util.List;
import java.util.UUID;

import com.agendaai.agendaai.model.Schedule;
import com.agendaai.agendaai.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.agendaai.agendaai.model.Job;
import com.agendaai.agendaai.repository.JobRepository;

import jakarta.transaction.Transactional;

@AllArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Job createJob(Job job) {
        scheduleRepository.save(job.getSchedule());
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

    @Transactional
    public Job updateSchedule(UUID jobId, Schedule schedule) {
        Job existingJob = getJobById(jobId);

        if (existingJob != null) {
            schedule.setId(existingJob.getSchedule().getId());
            existingJob.setSchedule(scheduleRepository.save(schedule));

            return jobRepository.save(existingJob);
        }
        return null;
    }

    public boolean deleteJob(UUID id) {
        Job existingJob = getJobById(id);

        if (existingJob != null) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
