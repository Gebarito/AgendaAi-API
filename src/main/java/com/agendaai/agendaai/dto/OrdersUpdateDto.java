package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Orders;

public record OrdersUpdateDto(
                              String status) {
    public Orders toOrders(){
        Orders orders = new Orders();
        orders.setStatus(status);
        return orders;
    }
}
