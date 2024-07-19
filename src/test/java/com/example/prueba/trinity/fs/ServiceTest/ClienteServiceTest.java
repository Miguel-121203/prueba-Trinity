package com.example.prueba.trinity.fs.ServiceTest;

import com.example.prueba.trinity.fs.Entity.Cliente;
import com.example.prueba.trinity.fs.IRepository.IClienteRepository;
import com.example.prueba.trinity.fs.Service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setTipoId("DNI");
        cliente.setNumId(Integer.parseInt("12345678"));
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setCorreo("juan.perez@example.com");
        cliente.setFechaNacimiento(LocalDate.of(2000, Month.JANUARY, 1));
        cliente.setFechaCreacion(LocalDateTime.now());
        cliente.setFechaModificacion(LocalDateTime.now());
    }

    //TEST AL SERVICIO BUSCAR TODOS
    @Test
    public void testFindAll() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        List<Cliente> clientes = clienteService.findAll();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    //TEST AL SERVICIO BUSCAR POR ID
    @Test
    public void testFindById() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> foundCliente = clienteService.findById(1L);

        assertTrue(foundCliente.isPresent());
        assertEquals("Juan", foundCliente.get().getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }

    //TEST AL SERVICIO DE GUARDAR
    @Test
    public void testSave() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente savedCliente = clienteService.save(cliente);

        assertNotNull(savedCliente);
        assertEquals("Juan", savedCliente.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }

    // TEST A LA VALIDACION DE MAYOR DE EDAD
    @Test
    public void testSave_ThrowsExceptionIfUnderage() {
        cliente.setFechaNacimiento(LocalDate.of(2010, Month.JANUARY, 1));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.save(cliente);
        });

        assertEquals("El cliente debe ser mayor de edad.", exception.getMessage());
    }

    //TEST AL SERVICIO DE ACTUALIZAR
    @Test
    public void testUpdate() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteDetalles = new Cliente();
        clienteDetalles.setTipoId("DNI");
        clienteDetalles.setNumId(Integer.parseInt("87654321"));
        clienteDetalles.setNombre("Pedro");
        clienteDetalles.setApellido("Gomez");
        clienteDetalles.setCorreo("pedro.gomez@example.com");
        clienteDetalles.setFechaNacimiento(LocalDate.of(1995, Month.JANUARY, 1));

        Cliente updatedCliente = clienteService.update(clienteDetalles, 1L);

        assertNotNull(updatedCliente);
        assertEquals("Pedro", updatedCliente.getNombre());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }

    //TEST AL SERVICIO DE BORRAR
    @Test
    public void testDelete() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.delete(1L);

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

}
