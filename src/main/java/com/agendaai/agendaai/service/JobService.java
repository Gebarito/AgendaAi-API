package com.agendaai.agendaai.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.agendaai.agendaai.model.Schedule;
import com.agendaai.agendaai.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
        job.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        scheduleRepository.save(job.getSchedule());
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository
                .findAllByOrderByDateCreated(PageRequest.of(0, 100));
    }

    public Job getJobById(UUID id) {
        return jobRepository
            .findById(id)
            .orElse(null);
    }

    public List<Job> getJobsByCategory(String category) {
        return jobRepository
                .findAllByCategory(category);
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
