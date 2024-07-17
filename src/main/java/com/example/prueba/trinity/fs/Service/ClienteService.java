package com.example.prueba.trinity.fs.Service;

import com.example.prueba.trinity.fs.Entity.Cliente;
import com.example.prueba.trinity.fs.IRepository.IClienteRepository;
import com.example.prueba.trinity.fs.IService.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ClienteService implements IClienteService {

    //Falta creacion de servicios personalizados 

    @Autowired
    private IClienteRepository repository;

    @Override
    public List<Cliente> findAll() {
        return List.of();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cliente save(Cliente order) {
        return null;
    }

    @Override
    public void update(Cliente cliente, Long id) {

    }

    @Override
    public void delete(Long id) {

    }
}
