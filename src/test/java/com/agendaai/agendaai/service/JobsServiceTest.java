package com.agendaai.agendaai.service;

import com.agendaai.agendaai.dto.BusinessRecordDto;
import com.agendaai.agendaai.model.*;
import com.agendaai.agendaai.repository.BusinessRepository;
import com.agendaai.agendaai.repository.JobsRepository;
import com.agendaai.agendaai.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobsServiceTest {

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private JobsRepository jobsRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    private JobsService jobsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jobsService = new JobsService(businessRepository, jobsRepository, scheduleRepository);
    }

//    @Test
//    void testCreateJob_WhenBusinessExists_ShouldCreateJob() {
//        String cnpj = "12345678000123";
//        Business business = new Business();
//        business.setCnpj(cnpj);
//
//        Jobs job = new Jobs();
//        job.setId(UUID.randomUUID());
//        job.setName("Job1");
//        job.setAmount(100.0f);
//        job.setCategory("Category1");
//        job.setDescription("Test job");
//        Schedule schedule = new Schedule();
//        job.setSchedule(schedule);
//        job.setOrders(List.of());
//        business.setJobs(List.of(job));
//
//        when(businessRepository.findByCnpj(cnpj)).thenReturn(business);
//        when(jobsRepository.save(any(Jobs.class))).thenReturn(job);
//        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
//
//        Jobs createdJob = jobsService.createJob(cnpj, job);
//
//        // Assert
//        assertNotNull(createdJob);
//        assertEquals("Job1", createdJob.getName());
//        verify(businessRepository, times(1)).save(any(Business.class));
//        verify(jobsRepository, times(1)).save(any(Jobs.class));
//        verify(scheduleRepository, times(1)).save(any(Schedule.class));
//    }

    @Test
    void testCreateJob_WhenBusinessDoesNotExist_ShouldReturnNull() {
        // Arrange
        String cnpj = "12345678000123";
        Jobs job = new Jobs();
        when(businessRepository.findByCnpj(cnpj)).thenReturn(null);

        // Act
        Jobs createdJob = jobsService.createJob(cnpj, job);

        // Assert
        assertNull(createdJob);
        verify(businessRepository, times(1)).findByCnpj(cnpj);
    }

    @Test
    void testGetAllJobs_ShouldReturnListOfJobs() {
        // Arrange
        Jobs job1 = new Jobs();
        Jobs job2 = new Jobs();
        List<Jobs> jobsList = Arrays.asList(job1, job2);
        when(jobsRepository.findAllByOrderByDateCreated(PageRequest.of(0, 100))).thenReturn(jobsList);

        // Act
        List<Jobs> allJobs = jobsService.getAllJobs();

        // Assert
        assertNotNull(allJobs);
        assertEquals(2, allJobs.size());
    }

    @Test
    void testGetJobById_ShouldReturnJob() {
        // Arrange
        UUID jobId = UUID.randomUUID();
        Jobs job = new Jobs();
        when(jobsRepository.findById(jobId)).thenReturn(Optional.of(job));

        // Act
        Jobs foundJob = jobsService.getJobById(jobId);

        // Assert
        assertNotNull(foundJob);
        assertEquals(job, foundJob);
    }

    @Test
    void testGetJobById_WhenJobNotFound_ShouldReturnNull() {
        // Arrange
        UUID jobId = UUID.randomUUID();
        when(jobsRepository.findById(jobId)).thenReturn(Optional.empty());

        // Act
        Jobs foundJob = jobsService.getJobById(jobId);

        // Assert
        assertNull(foundJob);
    }

    @Test
    void testUpdateJob_ShouldUpdateJobDetails() {
        // Arrange
        UUID jobId = UUID.randomUUID();
        Jobs existingJob = new Jobs();
        existingJob.setName("Old Job");
        existingJob.setAmount(50.0f);
        existingJob.setCategory("Category1");
        existingJob.setDescription("Old description");

        Jobs updatedJob = new Jobs();
        updatedJob.setName("Updated Job");
        updatedJob.setAmount(100.0f);
        updatedJob.setCategory("Category1");
        updatedJob.setDescription("Updated description");

        when(jobsRepository.findById(jobId)).thenReturn(Optional.of(existingJob));
        when(jobsRepository.save(any(Jobs.class))).thenReturn(updatedJob);

        // Act
        Jobs result = jobsService.updateJob(jobId, updatedJob);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Job", result.getName());
        assertEquals(100.0f, result.getAmount());
        assertEquals("Updated description", result.getDescription());
    }

    @Test
    void testUpdateJob_WhenJobNotFound_ShouldReturnNull() {
        // Arrange
        UUID jobId = UUID.randomUUID();
        Jobs updatedJob = new Jobs();

        when(jobsRepository.findById(jobId)).thenReturn(Optional.empty());

        // Act
        Jobs result = jobsService.updateJob(jobId, updatedJob);

        // Assert
        assertNull(result);
    }

    @Test
    void testUpdateSchedule_ShouldUpdateJobSchedule() {
        // Arrange
        UUID jobId = UUID.randomUUID();
        Jobs existingJob = new Jobs();
        Schedule oldSchedule = new Schedule();
        existingJob.setSchedule(oldSchedule);

        Schedule newSchedule = new Schedule();

        when(jobsRepository.findById(jobId)).thenReturn(Optional.of(existingJob));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(newSchedule);
        when(jobsRepository.save(any(Jobs.class))).thenReturn(existingJob);

        // Act
        Jobs result = jobsService.updateSchedule(jobId, newSchedule);

        // Assert
        assertNotNull(result);
        assertEquals(newSchedule, result.getSchedule());
    }

    @Test
    void testUpdateSchedule_WhenJobNotFound_ShouldReturnNull() {
        // Arrange
        UUID jobId = UUID.randomUUID();
        Schedule newSchedule = new Schedule();

        when(jobsRepository.findById(jobId)).thenReturn(Optional.empty());

        // Act
        Jobs result = jobsService.updateSchedule(jobId, newSchedule);

        // Assert
        assertNull(result);
    }
}
