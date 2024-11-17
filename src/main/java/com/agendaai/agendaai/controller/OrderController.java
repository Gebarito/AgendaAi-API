package com.agendaai.agendaai.controller;

import com.agendaai.agendaai.dto.OrderRecordDto;
import com.agendaai.agendaai.model.Order;
import com.agendaai.agendaai.model.Schedule;
import com.agendaai.agendaai.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRecordDto order) {
        Order tmpOrder = new Order();
        BeanUtils.copyProperties(order, tmpOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(tmpOrder));
    }

    @GetMapping("/orders")
    public ResponseEntity<ArrayList<Order>> getAllOrders() {
        ArrayList<Order> orders = (ArrayList<Order>) orderService.getAllOrders();

        if(orders == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        Order pModel = orderService.getOrderById(orderId);

        if(pModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(pModel);
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<Order> updateOrderInformationById(@PathVariable UUID orderId, @RequestBody Order pModel) {
        Order newModel = orderService.updateOrder(orderId, pModel);
        if (newModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(newModel);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Order> deleteOrderById(@PathVariable UUID orderId) {
        if (!orderService.deleteOrder(orderId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.noContent().build();
    }
}
