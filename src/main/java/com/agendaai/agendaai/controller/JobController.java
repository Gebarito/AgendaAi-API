package com.agendaai.agendaai.controller;

import java.util.ArrayList;
import java.util.UUID;

import com.agendaai.agendaai.model.Schedule;
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

    @PostMapping("/api/job")
    public ResponseEntity<Job> createJob(@RequestBody @Valid JobRecordDto job) {
        Job tmpJob = new Job();
        BeanUtils.copyProperties(job, tmpJob);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobService.createJob(tmpJob));
    }

    @GetMapping("/api/jobs")
    public ResponseEntity<ArrayList<Job>> getAllJobs() {
        ArrayList<Job> jobs = (ArrayList<Job>) jobService.getAllJobs();

        if(jobs == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(jobs);
    }

    @GetMapping("/api/job/{jobId}")
    public ResponseEntity<Job> getJobById(@PathVariable UUID jobId) {
        Job pModel = jobService.getJobById(jobId);

        if(pModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(pModel);
    }

    @PutMapping("/api/job/{jobId}")
    public ResponseEntity<Job> updateJobInformationById(@PathVariable UUID jobId, @RequestBody Job pModel) {
        Job newModel = jobService.updateJob(jobId, pModel);
        if (newModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(newModel);
    }

    @PutMapping("api/job/schedule/{jobId}")
    public ResponseEntity<Job> updateJobScheduleById(@PathVariable UUID jobId, @RequestBody Schedule schedule) {
        Job newModel = jobService.updateSchedule(jobId, schedule);
        if (newModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(newModel);
    }

    @DeleteMapping("/api/job/{jobId}")
    public ResponseEntity<Job> deleteJobById(@PathVariable UUID jobId) {
        if (!jobService.deleteJob(jobId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.noContent().build();
    }
}
