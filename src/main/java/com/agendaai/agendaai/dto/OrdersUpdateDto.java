package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Orders;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record OrdersUpdateDto(
                              String status) {
    public Orders toOrders(){
        Orders orders = new Orders();
        orders.setStatus(status);
        return orders;
    }
}
