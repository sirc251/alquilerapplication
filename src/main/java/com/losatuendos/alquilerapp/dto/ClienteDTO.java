package com.losatuendos.alquilerapp.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ClienteDTO {

    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String mail;
}
