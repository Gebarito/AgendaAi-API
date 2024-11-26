package com.agendaai.agendaai.service;

import com.agendaai.agendaai.dto.BusinessRecordDto;
import com.agendaai.agendaai.model.Business;
import com.agendaai.agendaai.model.Jobs;
import com.agendaai.agendaai.repository.BusinessRepository;
import com.agendaai.agendaai.repository.JobsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BusinessServiceTest {

    @Mock
    private JobsRepository jobsRepository;

    @Mock
    private BusinessRepository businessRepository;

    @InjectMocks
    private BusinessService businessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBusiness_NewBusiness() {
        // Arrange
        BusinessRecordDto dto = new BusinessRecordDto(
                "Business Name",
                "business@example.com",
                "123456789",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Business business = dto.toBusiness();
        when(businessRepository.findByCnpj("123456789")).thenReturn(null);
        when(businessRepository.save(any(Business.class))).thenReturn(business);

        Business savedBusiness = businessService.saveBusiness(dto.toBusiness());

        assertNotNull(savedBusiness);
        assertEquals(dto.name(), savedBusiness.getName());
        verify(businessRepository, times(1)).save(any(Business.class));
    }

    @Test
    void testSaveBusiness_ExistingBusiness() {
        // Arrange
        BusinessRecordDto dto = new BusinessRecordDto(
                "Business Name",
                "business@example.com",
                "123456789",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Business existingBusiness = dto.toBusiness();
        when(businessRepository.findByCnpj("123456789")).thenReturn(existingBusiness);

        Business result = businessService.saveBusiness(dto.toBusiness());

        assertNull(result);
        verify(businessRepository, never()).save(any());
    }

    @Test
    void testUpdateBusiness_ExistingBusiness() {
        // Arrange
        BusinessRecordDto dto = new BusinessRecordDto(
                "Updated Business Name",
                "updated@example.com",
                "123456789",
                "secure_password",
                "1234567890",
                "12345-678",
                null);
        Business existingBusiness = new Business();
        existingBusiness.setCnpj("123456789");
        existingBusiness.setId(1L);
        when(businessRepository.findByCnpj("123456789")).thenReturn(existingBusiness);
        when(businessRepository.save(any(Business.class))).thenReturn(existingBusiness);


        Business result = businessService.updateBusiness(dto.toBusiness());

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(dto.name(), result.getName());
        verify(businessRepository, times(1)).save(any(Business.class));
    }

    @Test
    void testUpdateBusiness_NonExistingBusiness() {
        // Arrange
        BusinessRecordDto dto = new BusinessRecordDto(
                "Updated Business Name",
                "updated@example.com",
                "123456789",
                "secure_password",
                "1234567890",
                "12345-678",
                "Updated Address"
        );
        when(businessRepository.findByCnpj("123456789")).thenReturn(null);

        Business result = businessService.updateBusiness(dto.toBusiness());

        assertNull(result);
        verify(businessRepository, never()).save(any());
    }

    @Test
    void testDeleteBusinessById_Success() {
        // Arrange
        BusinessRecordDto dto = new BusinessRecordDto(
                "Business Name",
                "business@example.com",
                "123456789",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Business business = dto.toBusiness();
        business.setId(1L);
        Jobs job = new Jobs();
        job.setId(UUID.fromString("8fa0e469-166c-47eb-b35a-bee79ed245de"));
        business.setJobs(Collections.singletonList(job));

        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        when(businessRepository.findJobsById(1L)).thenReturn(business);

        boolean result = businessService.deleteBusinessById(1L);

        assertTrue(result);
        verify(jobsRepository, times(1)).deleteById(UUID.fromString("8fa0e469-166c-47eb-b35a-bee79ed245de"));
        verify(businessRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBusinessById_BusinessNotFound() {
        // Arrange
        when(businessRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = businessService.deleteBusinessById(1L);

        assertFalse(result);
        verify(businessRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetBusinessById() {
        // Arrange
        BusinessRecordDto dto = new BusinessRecordDto(
                "Business Name",
                "business@example.com",
                "123456789",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Business business = dto.toBusiness();
        business.setId(1L);
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));

        Business result = businessService.getBusinessById(1L);

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
    }

    @Test
    void testGetBusinessByCnpj() {
        // Arrange
        BusinessRecordDto dto = new BusinessRecordDto(
                "Business Name",
                "business@example.com",
                "123456789",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Business business = dto.toBusiness();
        when(businessRepository.findByCnpj("123456789")).thenReturn(business);

        Business result = businessService.getBusinessByCnpj("123456789");

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
    }
}
