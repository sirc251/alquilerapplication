package com.losatuendos.alquilerapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("VESTIDO")
public class VestidoDama extends Prenda {
    private boolean pedreria;
    private String largo;
    private int numeroPiezas;

}