package com.agendaai.agendaai.service;

import com.agendaai.agendaai.model.Customers;
import com.agendaai.agendaai.model.Jobs;
import com.agendaai.agendaai.model.Orders;
import com.agendaai.agendaai.repository.CustomersRepository;
import com.agendaai.agendaai.repository.JobsRepository;
import com.agendaai.agendaai.repository.OrdersRepository;
import com.agendaai.agendaai.utils.OrdersInterface;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Service
public class OrdersService {
    private final JobsRepository jobsRepository;
    private final CustomersRepository customersRepository;
    private final OrdersRepository ordersRepository;

    @Transactional
    public Orders createOrder(String cpf, UUID jobsId, Orders orders) {
        Customers existingCustomer = customersRepository.findByCpf(cpf);
        if (existingCustomer == null) {
            log.error("F=createOrder M=Não foi possível encontrar o customer");
            return null;
        }
        List<Orders> ordersCustomer = existingCustomer.getOrders();
        Jobs existingJob = jobsRepository.findById(jobsId).orElse(null);
        if (existingJob == null) {
            log.error("F=createOrder M=Não foi possível encontrar o Jobs de ID {}", jobsId);
            return null;
        }
        orders.setJobs(existingJob);
        orders.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        orders.setStatus(OrdersInterface.PENDING);
        ordersRepository.save(orders);
        ordersCustomer.add(orders);
        existingCustomer.setOrders(ordersCustomer);
        return customersRepository.save(existingCustomer).getOrders().getLast(); //TODO: Não está salvando no customer
    }

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders getOrderById(UUID id) {
        return ordersRepository
                .findById(id)
                .orElse(null);
    }

    @Transactional
    public Orders updateOrder(UUID orderId, Orders orders) {
        Orders existingOrders = getOrderById(orderId);

        if (existingOrders != null) {
            existingOrders.setStatus(orders.getStatus());
            return ordersRepository.save(existingOrders);
        }

        return null;
    }
}
