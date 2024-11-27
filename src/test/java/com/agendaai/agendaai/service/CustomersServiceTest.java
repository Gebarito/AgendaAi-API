package com.agendaai.agendaai.service;

import com.agendaai.agendaai.dto.CustomersRecordDto;
import com.agendaai.agendaai.model.Customers;
import com.agendaai.agendaai.model.Orders;
import com.agendaai.agendaai.repository.CustomersRepository;
import com.agendaai.agendaai.repository.OrdersRepository;
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

public class CustomersServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private CustomersRepository customersRepository;

    @InjectMocks
    private CustomersService customersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCustomer_NewCustomer() {
        // Arrange
        CustomersRecordDto dto = new CustomersRecordDto(
                "Customer Name",
                "customer@example.com",
                "12345678900",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Customers customer = dto.toCustomers();
        when(customersRepository.findByCpf("12345678900")).thenReturn(null);
        when(customersRepository.save(any(Customers.class))).thenReturn(customer);

        // Act
        Customers savedCustomer = customersService.saveCustomer(dto.toCustomers());

        // Assert
        assertNotNull(savedCustomer);
        assertEquals(dto.name(), savedCustomer.getName());
        verify(customersRepository, times(1)).save(any(Customers.class));
    }

    @Test
    void testSaveCustomer_ExistingCustomer() {
        // Arrange
        CustomersRecordDto dto = new CustomersRecordDto(
                "Customer Name",
                "customer@example.com",
                "12345678900",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Customers existingCustomer = dto.toCustomers();
        when(customersRepository.findByCpf("12345678900")).thenReturn(existingCustomer);

        // Act
        Customers result = customersService.saveCustomer(dto.toCustomers());

        // Assert
        assertNull(result);
        verify(customersRepository, never()).save(any());
    }

    @Test
    void testUpdateCustomer_ExistingCustomer() {
        // Arrange
        CustomersRecordDto dto = new CustomersRecordDto(
                "Updated Customer Name",
                "updated@example.com",
                "12345678900",
                "secure_password",
                "1234567890",
                "12345-678",
                "Updated Address"
        );
        Customers existingCustomer = dto.toCustomers();
        existingCustomer.setName("Other name");
        existingCustomer.setId(1L);
        when(customersRepository.findByCpf("12345678900")).thenReturn(existingCustomer);
        when(customersRepository.save(any(Customers.class))).thenReturn(existingCustomer);


        Customers result = customersService.updateCustomer(dto.toCustomers());

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getName());
        assertNotEquals(dto.name(), result.getName());
        verify(customersRepository, times(1)).save(any(Customers.class));
    }

    @Test
    void testUpdateCustomer_NonExistingCustomer() {
        // Arrange
        CustomersRecordDto dto = new CustomersRecordDto(
                "Updated Customer Name",
                "updated@example.com",
                "12345678900",
                "secure_password",
                "1234567890",
                "12345-678",
                "Updated Address"
        );
        when(customersRepository.findByCpf("12345678900")).thenReturn(null);

        // Act
        Customers result = customersService.updateCustomer(dto.toCustomers());

        // Assert
        assertNull(result);
        verify(customersRepository, never()).save(any());
    }

    @Test
    void testDeleteCustomerById_Success() {
        // Arrange
        CustomersRecordDto dto = new CustomersRecordDto(
                "Customer Name",
                "customer@example.com",
                "12345678900",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Customers customer = dto.toCustomers();
        customer.setId(1L);
        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customersRepository.findOrdersById(1L)).thenReturn(customer);

        // Act
        boolean result = customersService.deleteCustomerById(1L);

        // Assert
        assertTrue(result);
        verify(ordersRepository, times(1)).deleteById(UUID.fromString("cb6bb3a3-b86c-4fd9-ba08-97d4aaebcf56"));
        verify(customersRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCustomerById_CustomerNotFound() {
        // Arrange
        when(customersRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        boolean result = customersService.deleteCustomerById(1L);

        // Assert
        assertFalse(result);
        verify(customersRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetCustomerById() {
        // Arrange
        CustomersRecordDto dto = new CustomersRecordDto(
                "Customer Name",
                "customer@example.com",
                "12345678900",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Customers customer = dto.toCustomers();
        customer.setId(1L);
        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        Customers result = customersService.getCustomerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
    }

    @Test
    void testGetCustomerByCpf() {
        // Arrange
        CustomersRecordDto dto = new CustomersRecordDto(
                "Customer Name",
                "customer@example.com",
                "12345678900",
                "secure_password",
                "1234567890",
                "12345-678",
                "Some Address"
        );
        Customers customer = dto.toCustomers();
        when(customersRepository.findByCpf("12345678900")).thenReturn(customer);

        // Act
        Customers result = customersService.getCustomerByCpf("12345678900");

        // Assert
        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
    }
}
