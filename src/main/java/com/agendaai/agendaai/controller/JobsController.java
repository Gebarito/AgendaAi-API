package com.agendaai.agendaai.controller;

import java.util.ArrayList;
import java.util.UUID;

import com.agendaai.agendaai.model.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.agendaai.agendaai.dto.JobsRecordDto;
import com.agendaai.agendaai.model.Jobs;
import com.agendaai.agendaai.service.JobsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@RestController
public class JobsController {

    private final JobsService jobsService;

    @PostMapping("/jobs/new")
    public ResponseEntity<Jobs> createJob(@RequestBody @Valid JobsRecordDto job) {
        Jobs newJob = jobsService.createJob(job.cnpj(), job.toJob());
        if (newJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newJob);
    }

    @GetMapping("/jobs")
    public ResponseEntity<ArrayList<Jobs>> getAHundredJobs() {
        ArrayList<Jobs> jobs = (ArrayList<Jobs>) jobsService.getAllJobs();

        if(jobs == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(jobs);
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Jobs> getJobById(@PathVariable UUID jobId) {
        Jobs jobs = jobsService.getJobById(jobId);

        if(jobs == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(jobs);
    }

    @GetMapping("/jobs/{jobCategory}")
    public ResponseEntity<ArrayList<Jobs>> getJobsByCategory(@PathVariable String jobCategory) {
        ArrayList<Jobs> jobs= (ArrayList<Jobs>) jobsService.getJobsByCategory(jobCategory);

        if(jobs == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(jobs);
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<Jobs> updateJobInformationById(@PathVariable UUID jobId, @RequestBody Jobs pModel) {
        Jobs newModel = jobsService.updateJob(jobId, pModel);
        if (newModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(newModel);
    }

    @PutMapping("/jobs/schedule/{jobId}")
    public ResponseEntity<Jobs> updateJobScheduleById(@PathVariable UUID jobId, @RequestBody Schedule schedule) {
        Jobs newModel = jobsService.updateSchedule(jobId, schedule);
        if (newModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(newModel);
    }
}
