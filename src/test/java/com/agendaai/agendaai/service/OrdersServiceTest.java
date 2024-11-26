package com.agendaai.agendaai.service;

import com.agendaai.agendaai.model.Customers;
import com.agendaai.agendaai.model.Jobs;
import com.agendaai.agendaai.model.Orders;
import com.agendaai.agendaai.repository.CustomersRepository;
import com.agendaai.agendaai.repository.JobsRepository;
import com.agendaai.agendaai.repository.OrdersRepository;
import com.agendaai.agendaai.utils.OrdersInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdersServiceTest {

    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private JobsRepository jobsRepository;

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private OrdersRepository ordersRepository;

    private Customers mockCustomer;
    private Jobs mockJob;
    private Orders mockOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCustomer = new Customers();
        mockCustomer.setCpf("12345678900");
        mockCustomer.setOrders(new ArrayList<>());

        mockJob = new Jobs();
        mockJob.setId(UUID.randomUUID());

        mockOrder = new Orders();
        mockOrder.setId(UUID.randomUUID());
        mockOrder.setAmount(150.0f);
        mockOrder.setDateAppointment(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        mockOrder.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        mockOrder.setStatus(OrdersInterface.PENDING);
    }

    @Test
    void createOrder_ValidCustomerAndJob_ReturnsOrder() {
        when(customersRepository.findByCpf("12345678900")).thenReturn(mockCustomer);
        when(jobsRepository.findById(mockJob.getId())).thenReturn(Optional.of(mockJob));
        when(ordersRepository.save(any(Orders.class))).thenReturn(mockOrder);

        Orders createdOrder = ordersService.createOrder("12345678900", mockJob.getId(), mockOrder);

        assertNotNull(createdOrder);
        assertEquals(mockOrder.getStatus(), createdOrder.getStatus());
        verify(ordersRepository, times(1)).save(mockOrder);
        verify(customersRepository, times(1)).save(mockCustomer);
    }

    @Test
    void createOrder_InvalidCustomer_ReturnsNull() {
        when(customersRepository.findByCpf("invalidCpf")).thenReturn(null);

        Orders createdOrder = ordersService.createOrder("invalidCpf", mockJob.getId(), mockOrder);

        assertNull(createdOrder);
        verify(customersRepository, times(1)).findByCpf("invalidCpf");
        verifyNoInteractions(jobsRepository, ordersRepository);
    }

    @Test
    void createOrder_InvalidJob_ReturnsNull() {
        when(customersRepository.findByCpf("12345678900")).thenReturn(mockCustomer);
        when(jobsRepository.findById(mockJob.getId())).thenReturn(Optional.empty());

        Orders createdOrder = ordersService.createOrder("12345678900", mockJob.getId(), mockOrder);

        assertNull(createdOrder);
        verify(jobsRepository, times(1)).findById(mockJob.getId());
        verifyNoInteractions(ordersRepository);
    }

    @Test
    void getAllOrders_ReturnsOrderList() {
        List<Orders> ordersList = List.of(mockOrder);
        when(ordersRepository.findAll()).thenReturn(ordersList);

        List<Orders> result = ordersService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockOrder.getId(), result.get(0).getId());
        verify(ordersRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_ValidId_ReturnsOrder() {
        when(ordersRepository.findById(mockOrder.getId())).thenReturn(Optional.of(mockOrder));

        Orders result = ordersService.getOrderById(mockOrder.getId());

        assertNotNull(result);
        assertEquals(mockOrder.getId(), result.getId());
        verify(ordersRepository, times(1)).findById(mockOrder.getId());
    }

    @Test
    void getOrderById_InvalidId_ReturnsNull() {
        when(ordersRepository.findById(UUID.randomUUID())).thenReturn(Optional.empty());

        Orders result = ordersService.getOrderById(UUID.randomUUID());

        assertNull(result);
        verify(ordersRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void updateOrder_ValidOrderId_ReturnsUpdatedOrder() {
        Orders updatedOrder = new Orders();
        updatedOrder.setStatus(OrdersInterface.APPROVED);

        when(ordersRepository.findById(mockOrder.getId())).thenReturn(Optional.of(mockOrder));
        when(ordersRepository.save(mockOrder)).thenReturn(mockOrder);

        Orders result = ordersService.updateOrder(mockOrder.getId(), updatedOrder);

        assertNotNull(result);
        assertEquals(OrdersInterface.APPROVED, result.getStatus());
        verify(ordersRepository, times(1)).save(mockOrder);
    }

    @Test
    void updateOrder_InvalidOrderId_ReturnsNull() {
        when(ordersRepository.findById(mockOrder.getId())).thenReturn(Optional.empty());

        Orders result = ordersService.updateOrder(mockOrder.getId(), mockOrder);

        assertNull(result);
        verify(ordersRepository, never()).save(any(Orders.class));
    }
}
