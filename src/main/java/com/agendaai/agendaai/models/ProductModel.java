package com.agendaai.agendaai.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "TB_PRODUCT")
public class ProductModel {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private float amount;
    private String description;

}
