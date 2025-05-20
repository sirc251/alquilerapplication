package com.losatuendos.alquilerapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.losatuendos.alquilerapp.dto.PrendaDTO;
import com.losatuendos.alquilerapp.factory.PrendaFactory;
import com.losatuendos.alquilerapp.id.ServicioAlquilerIDGenerator;
import com.losatuendos.alquilerapp.model.Cliente;
import com.losatuendos.alquilerapp.model.Empleado;
import com.losatuendos.alquilerapp.model.Prenda;
import com.losatuendos.alquilerapp.model.ServicioAlquiler;
import com.losatuendos.alquilerapp.model.VestidoDama;
import com.losatuendos.alquilerapp.pattern.LavadoImplementacion;
import com.losatuendos.alquilerapp.repository.ClienteRepository;
import com.losatuendos.alquilerapp.repository.EmpleadoRepository;
import com.losatuendos.alquilerapp.repository.PrendaRepository;
import com.losatuendos.alquilerapp.repository.ServicioAlquilerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NegocioAlquilerFacadeTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private PrendaRepository prendaRepository;

    @Mock
    private ServicioAlquilerRepository servicioAlquilerRepository;

    @Mock
    private ServicioAlquilerIDGenerator idGenerator;

    private NegocioAlquilerFacade facade;

    private final PrendaFactory prendaFactory = new PrendaFactory() {
        @Override
        public Prenda crearPrenda(PrendaDTO dto) {
            VestidoDama vestido = new VestidoDama();
            vestido.setRef(dto.getRef());
            vestido.setColor(dto.getColor());
            vestido.setMarca(dto.getMarca());
            vestido.setTalla(dto.getTalla());
            vestido.setValorAlquiler(dto.getValorAlquiler());
            vestido.setPedreria(false);
            vestido.setLargo("Mediano");
            vestido.setNumeroPiezas(1);
            return vestido;
        }
    };

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Map<String, PrendaFactory> factoryMap = Map.of("VestidoDama", prendaFactory);
        Map<String, LavadoImplementacion> lavadoMap = Map.of();


        facade = new NegocioAlquilerFacade(
                clienteRepository,
                empleadoRepository,
                prendaRepository,
                servicioAlquilerRepository,
                idGenerator,
                factoryMap,
                lavadoMap
        );
    }

    @Test
    void crearServicioAlquiler_UsandoVestidoDama() {
        String clienteId = "cliente1";
        String empleadoId = "empleado1";
        LocalDate fechaAlquiler = LocalDate.now();

        PrendaDTO dto = new PrendaDTO();
        dto.setTipoDiscriminador("VestidoDama");
        dto.setRef("V001");
        dto.setColor("Rojo");
        dto.setMarca("MarcaX");
        dto.setTalla("M");
        dto.setValorAlquiler(150.0);

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        Empleado empleado = new Empleado();
        empleado.setId(empleadoId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(empleadoRepository.findById(empleadoId)).thenReturn(Optional.of(empleado));
        when(prendaRepository.save(any(VestidoDama.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(idGenerator.getNextId()).thenReturn(123L);
        when(servicioAlquilerRepository.save(any(ServicioAlquiler.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ServicioAlquiler servicio = facade.crearServicioAlquiler(clienteId, empleadoId, List.of(dto), fechaAlquiler);

        assertNotNull(servicio);
        assertEquals(123L, servicio.getId());
        assertEquals(cliente, servicio.getCliente());
        assertEquals(empleado, servicio.getEmpleado());
        assertEquals(fechaAlquiler, servicio.getFechaAlqui());
        assertEquals(1, servicio.getPrendas().size());

        Prenda prenda = servicio.getPrendas().get(0);
        assertTrue(prenda instanceof VestidoDama);

        VestidoDama vestido = (VestidoDama) prenda;
        assertEquals("V001", vestido.getRef());
        assertEquals("Rojo", vestido.getColor());
        assertEquals("MarcaX", vestido.getMarca());
        assertEquals("M", vestido.getTalla());
        assertEquals(150.0, vestido.getValorAlquiler());

        verify(clienteRepository).findById(clienteId);
        verify(empleadoRepository).findById(empleadoId);
        verify(prendaRepository).save(prenda);
        verify(servicioAlquilerRepository).save(any(ServicioAlquiler.class));
        verify(idGenerator).getNextId();
    }
}
