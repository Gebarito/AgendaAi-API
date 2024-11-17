package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Job;
import com.agendaai.agendaai.utils.OrderInterface;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record OrderRecordDto(
                @NotNull float amount,
                @NotNull Timestamp dateAppointment,
                @NotNull Job job) {
}
