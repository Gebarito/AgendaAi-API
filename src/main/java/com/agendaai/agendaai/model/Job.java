package com.agendaai.agendaai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_JOB")
public class Job implements Serializable {
    @Serial
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;
    @NotNull
    private float amount;
    private String description;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Schedule schedule;

    @OneToMany
    private List<Order> orders;
}
