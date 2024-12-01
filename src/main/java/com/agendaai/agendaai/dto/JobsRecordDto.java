package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Jobs;
import com.agendaai.agendaai.model.Schedule;
import jakarta.validation.constraints.NotNull;

public record JobsRecordDto(
        @NotNull String cnpj,
        @NotNull String name,
        @NotNull float amount,
        @NotNull String category,
        boolean active,
        String description,
        Schedule schedule) {
    public Jobs toJob() {
        Jobs jobs = new Jobs();
        jobs.setName(name);
        jobs.setAmount(amount);
        jobs.setCategory(category);
        jobs.setDescription(description);
        jobs.setSchedule(schedule);
        return jobs; }
}
