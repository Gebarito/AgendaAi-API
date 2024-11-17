package com.agendaai.agendaai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_SCHEDULE")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int interval;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "monday_start")),
            @AttributeOverride(name = "end", column = @Column(name = "monday_end"))
    })
    @Embedded
    private RangeTime monday;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "tuesday_start")),
            @AttributeOverride(name = "end", column = @Column(name = "tuesday_end"))
    })
    @Embedded
    private RangeTime tuesday;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "wednesday_start")),
            @AttributeOverride(name = "end", column = @Column(name = "wednesday_end"))
    })
    @Embedded
    private RangeTime wednesday;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "thursday_start")),
            @AttributeOverride(name = "end", column = @Column(name = "thursday_end"))
    })
    @Embedded
    private RangeTime thursday;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "friday_start")),
            @AttributeOverride(name = "end", column = @Column(name = "friday_end"))
    })
    @Embedded
    private RangeTime friday;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "saturday_start")),
            @AttributeOverride(name = "end", column = @Column(name = "saturday_end"))
    })
    @Embedded
    private RangeTime saturday;

    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "sunday_start")),
            @AttributeOverride(name = "end", column = @Column(name = "sunday_end"))
    })
    @Embedded
    private RangeTime sunday;
}
