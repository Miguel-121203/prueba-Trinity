package com.example.prueba.trinity.fs.ServiceTest;

import com.example.prueba.trinity.fs.Entity.Enum.EstadoCuenta;
import com.example.prueba.trinity.fs.Entity.Enum.TipoCuenta;
import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.IRepository.IProductoRepository;
import com.example.prueba.trinity.fs.Service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private IProductoRepository repository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        productoService.findAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Producto producto = new Producto();
        when(repository.findById(anyLong())).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(producto, result.get());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void testSave() {
        Producto producto = new Producto();
        producto.setTipoCuenta(TipoCuenta.AHORRO);
        producto.setSaldo(1000.0);

        when(repository.save(any(Producto.class))).thenReturn(producto);

        Producto savedProducto = productoService.save(producto);

        assertNotNull(savedProducto);
        assertEquals(TipoCuenta.AHORRO, savedProducto.getTipoCuenta());
        assertEquals(EstadoCuenta.ACTIVA, savedProducto.getEstadoCuenta());
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testSaveWithNegativeSaldo() {
        Producto producto = new Producto();
        producto.setTipoCuenta(TipoCuenta.AHORRO);
        producto.setSaldo((double) -100);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> productoService.save(producto),
                "Expected save() to throw, but it didn't"
        );

        assertEquals("La cuenta de ahorro no puede tener un saldo menor a $0", thrown.getMessage());
        verify(repository, never()).save(any(Producto.class));
    }

    @Test
    public void testUpdate() {
        Producto producto = new Producto();
        producto.setTipoCuenta(TipoCuenta.CORRIENTE);
        producto.setEstadoCuenta(EstadoCuenta.ACTIVA);
        producto.setSaldo(500.0);
        producto.setExentaGmf(false);

        when(repository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(repository.save(any(Producto.class))).thenReturn(producto);

        Producto updatedProducto = productoService.update(producto, 1L);

        assertNotNull(updatedProducto);
        assertEquals(TipoCuenta.CORRIENTE, updatedProducto.getTipoCuenta());
        assertEquals(EstadoCuenta.ACTIVA, updatedProducto.getEstadoCuenta());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testUpdateNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> productoService.update(new Producto(), 1L),
                "Expected update() to throw, but it didn't"
        );

        assertEquals("Producto no encontrado con id 1", thrown.getMessage());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).save(any(Producto.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(repository).deleteById(anyLong());
        productoService.delete(1L);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testActivateProducto() {
        Producto producto = new Producto();
        when(repository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(repository.save(any(Producto.class))).thenReturn(producto);

        productoService.activateProducto(1L);

        assertEquals(EstadoCuenta.ACTIVA, producto.getEstadoCuenta());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testActivateProductoNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> productoService.activateProducto(1L),
                "Expected activateProducto() to throw, but it didn't"
        );

        assertEquals("Producto no encontrado con id 1", thrown.getMessage());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).save(any(Producto.class));
    }

    @Test
    public void testDeactivateProducto() {
        Producto producto = new Producto();
        when(repository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(repository.save(any(Producto.class))).thenReturn(producto);

        productoService.deactivateProducto(1L);

        assertEquals(EstadoCuenta.INACTIVA, producto.getEstadoCuenta());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testDeactivateProductoNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> productoService.deactivateProducto(1L),
                "Expected deactivateProducto() to throw, but it didn't"
        );

        assertEquals("Producto no encontrado con id 1", thrown.getMessage());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).save(any(Producto.class));
    }

    @Test
    public void testCancelateProducto() {
        Producto producto = new Producto();
        producto.setSaldo((double) 0);
        when(repository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(repository.save(any(Producto.class))).thenReturn(producto);

        productoService.cancelateProducto(1L);

        assertEquals(EstadoCuenta.CANCELADA, producto.getEstadoCuenta());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testCancelateProductoWithNonZeroSaldo() {
        Producto producto = new Producto();
        producto.setSaldo(100.0);
        when(repository.findById(anyLong())).thenReturn(Optional.of(producto));

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> productoService.cancelateProducto(1L),
                "Expected cancelateProducto() to throw, but it didn't"
        );

        assertEquals("No se puede cancelar una cuenta con saldo diferente a 0", thrown.getMessage());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).save(any(Producto.class));
    }

    @Test
    public void testCancelateProductoNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> productoService.cancelateProducto(1L),
                "Expected cancelateProducto() to throw, but it didn't"
        );

        assertEquals("Producto no encontrado con id 1", thrown.getMessage());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).save(any(Producto.class));
    }
}
