package com.agendaai.agendaai.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.sql.Timestamp;

@Embeddable
@Data
public class RangeTime {
    private Timestamp start;
    private Timestamp end;
}
