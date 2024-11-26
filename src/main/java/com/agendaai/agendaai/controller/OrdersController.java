package com.agendaai.agendaai.controller;

import com.agendaai.agendaai.dto.OrdersRecordDto;
import com.agendaai.agendaai.dto.OrdersUpdateDto;
import com.agendaai.agendaai.model.Orders;
import com.agendaai.agendaai.service.OrdersService;
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
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("/orders/new")
    public ResponseEntity<Orders> createOrder(@RequestBody @Valid OrdersRecordDto order) {
        Orders newOrder = ordersService.createOrder(order.cpf(),order.jobsId(), order.toOrders());
        if (newOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newOrder);
    }

    @GetMapping("/orders")
    public ResponseEntity<ArrayList<Orders>> getAllOrders() {
        ArrayList<Orders> orders = (ArrayList<Orders>) ordersService.getAllOrders();

        if(orders == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable UUID orderId) {
        Orders pModel = ordersService.getOrderById(orderId);

        if(pModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(pModel);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Orders> updateOrderInformationById(@PathVariable UUID orderId, @RequestBody OrdersUpdateDto pOrders) {
        Orders newModel = ordersService.updateOrder(orderId, pOrders.toOrders());
        if (newModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(newModel);
    }
}
