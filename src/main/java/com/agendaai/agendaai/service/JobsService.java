package com.agendaai.agendaai.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.agendaai.agendaai.model.*;
import com.agendaai.agendaai.repository.BusinessRepository;
import com.agendaai.agendaai.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.agendaai.agendaai.repository.JobsRepository;

import jakarta.transaction.Transactional;

@Log4j2
@AllArgsConstructor
@Service
public class JobsService {

    private final BusinessRepository businessRepository;
    private final JobsRepository jobsRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Jobs createJob(String cnpj, Jobs jobs) {
        Business existingBusiness = businessRepository.findByCnpj(cnpj);
        if (existingBusiness == null) {
            log.error("F=createJob M=Não foi possível encontrar o Business");
            return null;
        }
        jobs.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        scheduleRepository.save(jobs.getSchedule());
        List<Jobs> listOfBusinessJobs = existingBusiness.getJobs();
        listOfBusinessJobs.add(jobsRepository.save(jobs));
        existingBusiness.setJobs(listOfBusinessJobs);
        return businessRepository.save(existingBusiness).getJobs().getLast(); //TODO: Não está salvando no business
    }

    public List<Jobs> getAllJobs() {
        return jobsRepository
                .findAllByOrderByDateCreated(PageRequest.of(0, 100));
    }

    public Jobs getJobById(UUID id) {
        return jobsRepository
            .findById(id)
            .orElse(null);
    }

    public List<Jobs> getJobsByCategory(String category) {
        return jobsRepository
                .findAllByCategory(category);
    }

    @Transactional
    public Jobs updateJob(UUID jobId, Jobs jobs) {
        Jobs existingJobs = getJobById(jobId);

        if (existingJobs != null) {
            existingJobs.setName(jobs.getName());
            existingJobs.setAmount(jobs.getAmount());
            existingJobs.setDescription(jobs.getDescription());

            return jobsRepository.save(existingJobs);
        }

        return null;
    }

    @Transactional
    public Jobs updateSchedule(UUID jobId, Schedule schedule) {
        Jobs existingJobs = getJobById(jobId);

        if (existingJobs != null) {
            schedule.setId(existingJobs.getSchedule().getId());
            existingJobs.setSchedule(scheduleRepository.save(schedule));

            return jobsRepository.save(existingJobs);
        }
        return null;
    }
}
