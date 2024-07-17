package com.example.prueba.trinity.fs.Controller;

import com.example.prueba.trinity.fs.Entity.Cliente;
import com.example.prueba.trinity.fs.IService.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    @Autowired
    private IClienteService service;

    @GetMapping()
    public List<Cliente> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cliente> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public ResponseEntity<Cliente> save (@Valid @RequestBody Cliente cliente) {
        Cliente newCliente = service.save(cliente);
        return ResponseEntity.ok(newCliente);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Cliente cliente, @PathVariable Long id) {
        service.update(cliente  , id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
