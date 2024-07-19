package com.example.prueba.trinity.fs.ControllerTest;

import com.example.prueba.trinity.fs.Controller.ClienteController;
import com.example.prueba.trinity.fs.Entity.Cliente;
import com.example.prueba.trinity.fs.IService.IClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        Cliente cliente1 = new Cliente(); // Asegúrate de inicializar con datos válidos
        Cliente cliente2 = new Cliente();
        when(clienteService.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        mockMvc.perform(get("/api/cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(clienteService, times(1)).findAll();
    }

    @Test
    public void testFindById() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");

        when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Perez"));

        verify(clienteService, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        when(clienteService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cliente/1"))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).findById(anyLong());
    }

    @Test
    public void testSave() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setTipoId("DNI");
        cliente.setCorreo("juan.perez@example.com");
        // Rellena otros campos si es necesario

        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.tipoId").value("DNI"))
                .andExpect(jsonPath("$.correo").value("juan.perez@example.com"));

        verify(clienteService, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testUpdate() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setTipoId("DNI");
        cliente.setCorreo("juan.perez@example.com");

        when(clienteService.update(any(Cliente.class), anyLong())).thenReturn(cliente);

        String clienteJson = new ObjectMapper().writeValueAsString(cliente);

        mockMvc.perform(put("/api/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.tipoId").value("DNI"))
                .andExpect(jsonPath("$.correo").value("juan.perez@example.com"));

        verify(clienteService, times(1)).update(any(Cliente.class), eq(1L));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(clienteService).delete(anyLong());

        mockMvc.perform(delete("/api/cliente/1"))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).delete(anyLong());
    }
}