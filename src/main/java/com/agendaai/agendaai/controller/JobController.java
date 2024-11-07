package com.agendaai.agendaai.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.agendaai.agendaai.dto.JobRecordDto;
import com.agendaai.agendaai.model.Job;
import com.agendaai.agendaai.service.JobService;

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
public class JobController {

    private final JobService jobService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/api/jobs")
    public ResponseEntity createJob(
        @RequestBody @Valid JobRecordDto jobRecordDto
        ) {
        Job tmpJob = new Job();
        BeanUtils.copyProperties(jobRecordDto, tmpJob);
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(jobService
            .createJob(tmpJob));
    }

    @GetMapping("/api/jobs")
    public ResponseEntity<ArrayList<Job>> getAllJobs() {
        ArrayList<Job> jobs = (ArrayList<Job>) jobService.getAllJobs();

        if(jobs != null)
            return ResponseEntity.ok(jobs);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/api/jobs/{jobId}")
    public ResponseEntity<Job> getJobById(@PathVariable UUID jobId) {
        Job pModel = jobService.getJobById(jobId);
        if(pModel != null)
            return ResponseEntity.ok(pModel);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/api/jobs/{jobId}")
    public ResponseEntity<Job> updateJobById(@PathVariable UUID jobId, @RequestBody Job pModel) {
        Job newModel = jobService.updateJob(jobId, pModel);
        
        return ResponseEntity.ok(newModel);
    }

    @DeleteMapping("/api/jobs/{jobId}")
    public ResponseEntity<Job> deleteJobById(@PathVariable UUID jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }
    
    


}
