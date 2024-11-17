package com.agendaai.agendaai.service;

import com.agendaai.agendaai.model.Order;
import com.agendaai.agendaai.model.Schedule;
import com.agendaai.agendaai.repository.OrderRepository;
import com.agendaai.agendaai.utils.OrderInterface;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        order.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        order.setStatus(OrderInterface.PENDING);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID id) {
        return orderRepository
                .findById(id)
                .orElse(null);
    }

    @Transactional
    public Order updateOrder(UUID orderId, Order order) {
        Order existingOrder = getOrderById(orderId);

        if (existingOrder != null) {
            existingOrder.setDateAppointment(order.getDateAppointment());
            existingOrder.setStatus(order.getStatus());

            return orderRepository.save(existingOrder);
        }

        return null;
    }


    public boolean deleteOrder(UUID id) {
        Order existingOrder = getOrderById(id);

        if (existingOrder != null) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
