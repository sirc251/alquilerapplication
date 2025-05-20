package com.losatuendos.alquilerapp.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losatuendos.alquilerapp.model.Cliente;
import com.losatuendos.alquilerapp.service.NegocioAlquilerFacade;
import com.losatuendos.alquilerapp.web.dto.ClienteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NegocioAlquilerFacade facade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearCliente() throws Exception {
        ClienteRequest request = new ClienteRequest();
        request.setId("C123");
        request.setNombre("Juan Pérez");
        request.setDireccion("Calle 123");
        request.setTelefono("3214567890");
        request.setMail("juan@correo.com");

        Cliente clienteEsperado = new Cliente();
        clienteEsperado.setId("C123");
        clienteEsperado.setNombre("Juan Pérez");
        clienteEsperado.setDireccion("Calle 123");
        clienteEsperado.setTelefono("3214567890");
        clienteEsperado.setMail("juan@correo.com");

        when(facade.registrarCliente(any())).thenReturn(clienteEsperado);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("C123"))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.mail").value("juan@correo.com"));
    }
    @Test
    void testBuscarCliente() throws Exception {
        // Cliente esperado al buscar por ID
        Cliente clienteEsperado = new Cliente();
        clienteEsperado.setId("C123");
        clienteEsperado.setNombre("Juan Pérez");
        clienteEsperado.setDireccion("Calle 123");
        clienteEsperado.setTelefono("3214567890");
        clienteEsperado.setMail("juan@correo.com");

        // Simular respuesta de la fachada
        when(facade.consultarCliente("C123")).thenReturn(clienteEsperado);

        // Realizar la petición GET
        mockMvc.perform(get("/api/clientes/C123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("C123"))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.direccion").value("Calle 123"))
                .andExpect(jsonPath("$.telefono").value("3214567890"))
                .andExpect(jsonPath("$.mail").value("juan@correo.com"));
    }


}
