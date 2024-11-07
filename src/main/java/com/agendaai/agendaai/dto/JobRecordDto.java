package com.agendaai.agendaai.dto;

import jakarta.validation.constraints.NotNull;

public record JobRecordDto(
        @NotNull String name,
        @NotNull float amount,
        String description
) {
}
