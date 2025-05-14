package com.losatuendos.alquilerapp.web.dto;

import lombok.Data;

@Data
public class EmpleadoRequest {
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String cargo;
}