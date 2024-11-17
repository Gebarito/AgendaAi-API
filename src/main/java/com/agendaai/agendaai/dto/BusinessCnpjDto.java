package com.agendaai.agendaai.dto;

import jakarta.validation.constraints.NotNull;

public record BusinessCnpjDto(@NotNull String cnpj) {
}
