package com.example.prueba.trinity.fs.IService;

import com.example.prueba.trinity.fs.Entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    Cliente save(Cliente order);

    void update(Cliente cliente, Long id);

    void delete(Long id);
}
