package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Schedule;
import jakarta.validation.constraints.NotNull;

public record JobRecordDto(
        @NotNull String name,
        @NotNull float amount,
        @NotNull String category,
        String description,
        Schedule schedule
) {
}
