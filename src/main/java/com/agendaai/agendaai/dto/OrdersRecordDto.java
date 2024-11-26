package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Orders;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.UUID;

public record OrdersRecordDto(
                @NotNull String cpf,
                @NotNull float amount,
                @NotNull Timestamp dateAppointment,
                @NotNull UUID jobsId) {
    public Orders toOrders() {
        Orders orders = new Orders();
        orders.setAmount(amount);
        orders.setDateAppointment(dateAppointment);
        return orders; }
}
