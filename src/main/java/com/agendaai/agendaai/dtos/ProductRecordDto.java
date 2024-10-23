package com.agendaai.agendaai.dtos;

public record ProductRecordDto(
    long id,
    String name,
    float amount,
    String description
) {
}
