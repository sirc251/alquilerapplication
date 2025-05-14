package com.losatuendos.alquilerapp.web.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AlquilerRequest {
    private String clienteId;
    private String empleadoId;
    private List<PrendaRequest> prendas;
    private LocalDate fechaAlqui;
}