package com.losatuendos.alquilerapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TRAJE")
public class TrajeCaballero extends Prenda {
    private String tipoTraje;
    private String accesorio;

}
