package com.example.prueba.trinity.fs.Controller;

import com.example.prueba.trinity.fs.Dto.TransaccionRequestDto;
import com.example.prueba.trinity.fs.IService.ITransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private ITransaccionService transaccionService;


    @PostMapping("/consignacion")
    public ResponseEntity<String> consignacion(@RequestBody TransaccionRequestDto transaccionRequestDto) {
        transaccionService.consignacion(transaccionRequestDto);
        return ResponseEntity.ok("Consignación realizada exitosamente");
    }

    @PostMapping("/retiro")
    public ResponseEntity<String> retiro(@RequestBody TransaccionRequestDto transaccionRequestDto) {
        transaccionService.retiro(transaccionRequestDto);
        return ResponseEntity.ok("Retiro realizado exitosamente");
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> transferencia(@RequestBody TransaccionRequestDto transaccionRequestDto) {
        transaccionService.transferencia(transaccionRequestDto);
        return ResponseEntity.ok("Transferencia realizada exitosamente");
    }
}
