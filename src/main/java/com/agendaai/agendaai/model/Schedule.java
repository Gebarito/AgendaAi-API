//package com.agendaai.agendaai.model;
//
//import jakarta.persistence.Embedded;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.Serializable;
//import java.util.UUID;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "TB_SCHEDULE")
//public class Schedule implements Serializable {
//
//    @Id
//    private UUID id;
//    private int interval;
//
//    @Embedded
//    private RangeTime monday;
//    @Embedded
//    private RangeTime wednesday;
//    @Embedded
//    private RangeTime tuesday;
//    @Embedded
//    private RangeTime thursday;
//    @Embedded
//    private RangeTime friday;
//    @Embedded
//    private RangeTime saturday;
//    @Embedded
//    private RangeTime sunday;
//}
