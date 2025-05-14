package com.losatuendos.alquilerapp.web.dto;

import lombok.Data;

@Data
public class PrendaRequest {
    private String ref;
    private String color;
    private String marca;
    private String talla;
    private double valorAlquiler;
    private Boolean pedreria;
    private String largo;
    private Integer numeroPiezas;
    private String tipoTraje;
    private String accesorio;
    private String nombreDisfraz;
    private String tipoDiscriminador;
}
