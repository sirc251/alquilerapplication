package com.losatuendos.alquilerapp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PrendaDTO {
    private String ref;
    private String color;
    private String marca;
    private String talla;
    private double valorAlquiler;
    // atributos específicos
    private Boolean pedreria;
    private String largo;
    private Integer numeroPiezas;
    private String tipoTraje;
    private String accesorio;
    private String nombreDisfraz;
    // El tipo discriminador debe coincidir con (vestido, traje o disfraz)
    private String tipoDiscriminador;
}
