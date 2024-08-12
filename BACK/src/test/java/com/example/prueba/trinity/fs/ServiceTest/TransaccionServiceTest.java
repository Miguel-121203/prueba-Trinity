package com.example.prueba.trinity.fs.ServiceTest;

import com.example.prueba.trinity.fs.Dto.TransaccionRequestDto;
import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.Entity.Transaccion;
import com.example.prueba.trinity.fs.IRepository.IProductoRepository;
import com.example.prueba.trinity.fs.IRepository.ITransaccionRepository;
import com.example.prueba.trinity.fs.Service.TransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceTest {

    @InjectMocks
    private TransaccionService transaccionService;

    @Mock
    private IProductoRepository productoRepository;

    @Mock
    private ITransaccionRepository transaccionRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void testConsignacion() {
        Producto producto = new Producto();
        producto.setSaldo(100.0);

        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoId(1L);
        requestDto.setMonto(50.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        transaccionService.consignacion(requestDto);

        verify(productoRepository, times(1)).save(producto);
        verify(transaccionRepository, times(1)).save(any(Transaccion.class));
    }

    @Test
    @Transactional
    public void testRetiro() {
        Producto producto = new Producto();
        producto.setSaldo(100.0);

        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoId(1L);
        requestDto.setMonto(50.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        transaccionService.retiro(requestDto);

        verify(productoRepository, times(1)).save(producto);
        verify(transaccionRepository, times(1)).save(any(Transaccion.class));
    }

    @Test
    public void testRetiroSaldoInsuficiente() {
        Producto producto = new Producto();
        producto.setSaldo(30.0);

        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoId(1L);
        requestDto.setMonto(50.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            transaccionService.retiro(requestDto);
        });

        assertEquals("Saldo insuficiente", thrown.getMessage());
    }

    @Test
    @Transactional
    public void testTransferencia() {
        Producto productoOrigen = new Producto();
        productoOrigen.setSaldo(100.0);
        Producto productoDestino = new Producto();
        productoDestino.setSaldo(50.0);

        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoOrigenId(1L);
        requestDto.setProductoDestinoId(2L);
        requestDto.setMonto(30.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoOrigen));
        when(productoRepository.findById(2L)).thenReturn(Optional.of(productoDestino));

        transaccionService.transferencia(requestDto);

        verify(productoRepository, times(2)).save(any(Producto.class));
        verify(transaccionRepository, times(2)).save(any(Transaccion.class));
    }

    @Test
    public void testTransferenciaSaldoInsuficiente() {
        Producto productoOrigen = new Producto();
        productoOrigen.setSaldo(20.0);
        Producto productoDestino = new Producto();
        productoDestino.setSaldo(50.0);

        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoOrigenId(1L);
        requestDto.setProductoDestinoId(2L);
        requestDto.setMonto(30.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoOrigen));
        when(productoRepository.findById(2L)).thenReturn(Optional.of(productoDestino));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            transaccionService.transferencia(requestDto);
        });

        assertEquals("Saldo insuficiente en la cuenta origen", thrown.getMessage());
    }

    @Test
    public void testTransferenciaProductoNoEncontrado() {
        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoOrigenId(1L);
        requestDto.setProductoDestinoId(2L);
        requestDto.setMonto(30.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.empty());
        when(productoRepository.findById(2L)).thenReturn(Optional.of(new Producto()));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            transaccionService.transferencia(requestDto);
        });

        assertEquals("Producto origen o destino no encontrado", thrown.getMessage());
    }
}
