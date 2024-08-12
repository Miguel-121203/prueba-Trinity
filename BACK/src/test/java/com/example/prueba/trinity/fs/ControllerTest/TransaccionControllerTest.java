package com.example.prueba.trinity.fs.ControllerTest;

import com.example.prueba.trinity.fs.Dto.TransaccionRequestDto;
import com.example.prueba.trinity.fs.IService.ITransaccionService;
import com.example.prueba.trinity.fs.Controller.TransaccionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransaccionControllerTest {

    @InjectMocks
    private TransaccionController transaccionController;

    @Mock
    private ITransaccionService transaccionService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transaccionController).build();
    }

    @Test
    public void testConsignacion() throws Exception {
        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoId(1L);
        requestDto.setMonto(100.0);

        doNothing().when(transaccionService).consignacion(any(TransaccionRequestDto.class));

        mockMvc.perform(post("/api/transacciones/consignacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"productoId\": 1,"
                                + "\"monto\": 100.0"
                                + "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Consignación realizada exitosamente"));

        verify(transaccionService, times(1)).consignacion(any(TransaccionRequestDto.class));
    }

    @Test
    public void testRetiro() throws Exception {
        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoId(1L);
        requestDto.setMonto(50.0);

        doNothing().when(transaccionService).retiro(any(TransaccionRequestDto.class));

        mockMvc.perform(post("/api/transacciones/retiro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"productoId\": 1,"
                                + "\"monto\": 50.0"
                                + "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Retiro realizado exitosamente"));

        verify(transaccionService, times(1)).retiro(any(TransaccionRequestDto.class));
    }

    @Test
    public void testTransferencia() throws Exception {
        TransaccionRequestDto requestDto = new TransaccionRequestDto();
        requestDto.setProductoOrigenId(1L);
        requestDto.setProductoDestinoId(2L);
        requestDto.setMonto(30.0);

        doNothing().when(transaccionService).transferencia(any(TransaccionRequestDto.class));

        mockMvc.perform(post("/api/transacciones/transferencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"productoOrigenId\": 1,"
                                + "\"productoDestinoId\": 2,"
                                + "\"monto\": 30.0"
                                + "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transferencia realizada exitosamente"));

        verify(transaccionService, times(1)).transferencia(any(TransaccionRequestDto.class));
    }
}
