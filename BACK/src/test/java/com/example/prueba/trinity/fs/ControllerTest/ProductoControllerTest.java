package com.example.prueba.trinity.fs.ControllerTest;

import com.example.prueba.trinity.fs.Controller.ProductoController;
import com.example.prueba.trinity.fs.Entity.Cliente;
import com.example.prueba.trinity.fs.Entity.Enum.EstadoCuenta;
import com.example.prueba.trinity.fs.Entity.Enum.TipoCuenta;
import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.IService.IProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductoControllerTest {

    @Mock
    private IProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto());
        when(productoService.findAll()).thenReturn(productos);

        mockMvc.perform(get("/api/producto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());

        verify(productoService, times(1)).findAll();
    }

    @Test
    public void testFindById() throws Exception {
        Producto producto = new Producto();
        when(productoService.findById(anyLong())).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/api/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(productoService, times(1)).findById(anyLong());
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        when(productoService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/producto/1"))
                .andExpect(status().isNotFound());

        verify(productoService, times(1)).findById(anyLong());
    }

    @Test
    public void testSave() throws Exception {
        Producto producto = new Producto();
        producto.setTipoCuenta(TipoCuenta.AHORRO);
        producto.setEstadoCuenta(EstadoCuenta.ACTIVA);
        producto.setExentaGmf(true);
        producto.setCliente(new Cliente());

        when(productoService.save(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"tipoCuenta\": \"AHORRO\","
                                + "\"estadoCuenta\": \"ACTIVA\","
                                + "\"exentaGmf\": true,"
                                + "\"cliente\": {\"id\": 1}"
                                + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoCuenta", is("AHORRO")))
                .andExpect(jsonPath("$.estadoCuenta", is("ACTIVA")))
                .andExpect(jsonPath("$.exentaGmf", is(true)));

        verify(productoService, times(1)).save(any(Producto.class));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(productoService).delete(anyLong());

        mockMvc.perform(delete("/api/producto/1"))
                .andExpect(status().isNoContent());

        verify(productoService, times(1)).delete(anyLong());
    }

    @Test
    public void testActivateProducto() throws Exception {
        doNothing().when(productoService).activateProducto(anyLong());

        mockMvc.perform(put("/api/producto/activar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto activado exitosamente"));

        verify(productoService, times(1)).activateProducto(anyLong());
    }

    @Test
    public void testActivateProductoNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Producto no encontrado")).when(productoService).activateProducto(anyLong());

        mockMvc.perform(put("/api/producto/activar/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Producto no encontrado"));

        verify(productoService, times(1)).activateProducto(anyLong());
    }

    @Test
    public void testDeactivateProducto() throws Exception {
        doNothing().when(productoService).deactivateProducto(anyLong());

        mockMvc.perform(put("/api/producto/desactivar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto desactivado exitosamente"));

        verify(productoService, times(1)).deactivateProducto(anyLong());
    }

    @Test
    public void testDeactivateProductoNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Producto no encontrado")).when(productoService).deactivateProducto(anyLong());

        mockMvc.perform(put("/api/producto/desactivar/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Producto no encontrado"));

        verify(productoService, times(1)).deactivateProducto(anyLong());
    }

    @Test
    public void testCancelateProducto() throws Exception {
        doNothing().when(productoService).cancelateProducto(anyLong());

        mockMvc.perform(put("/api/producto/cancelar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto cancelado exitosamente"));

        verify(productoService, times(1)).cancelateProducto(anyLong());
    }

    @Test
    public void testCancelateProductoNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Producto no encontrado")).when(productoService).cancelateProducto(anyLong());

        mockMvc.perform(put("/api/producto/cancelar/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Producto no encontrado"));

        verify(productoService, times(1)).cancelateProducto(anyLong());
    }

    @Test
    public void testCancelateProductoWithNonZeroSaldo() throws Exception {
        doThrow(new IllegalStateException("No se puede cancelar una cuenta con saldo diferente a 0")).when(productoService).cancelateProducto(anyLong());

        mockMvc.perform(put("/api/producto/cancelar/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se puede cancelar una cuenta con saldo diferente a 0"));

        verify(productoService, times(1)).cancelateProducto(anyLong());
    }
}
