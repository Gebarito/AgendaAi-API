package com.agendaai.agendaai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_ORDER")
public class Orders implements Serializable {
    @Serial
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private float amount;
    @NotNull
    private String status;
    @NotNull
    private Timestamp dateCreated;
    @NotNull
    private Timestamp dateAppointment;

    @NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Jobs jobs;
}
