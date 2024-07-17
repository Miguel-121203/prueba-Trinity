package com.example.prueba.trinity.fs.Service;

import com.example.prueba.trinity.fs.Entity.Cliente;
import com.example.prueba.trinity.fs.IRepository.IClienteRepository;
import com.example.prueba.trinity.fs.IService.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    //Falta creacion de servicios personalizados

    @Autowired
    private IClienteRepository repository;

    @Override
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Cliente save(@Valid Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente update(@Valid Cliente clienteDetalles, Long id) {
        Optional<Cliente> clienteOpt = repository.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setTipoId(clienteDetalles.getTipoId());
            cliente.setNumId(clienteDetalles.getNumId());
            cliente.setNombre(clienteDetalles.getNombre());
            cliente.setApellido(clienteDetalles.getApellido());
            cliente.setCorreo(clienteDetalles.getCorreo());
            cliente.setFechaNacimiento(clienteDetalles.getFechaNacimiento());
            cliente.setFechaModificacion(LocalDateTime.now());
            return repository.save(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado con id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Cliente> clienteOpt = repository.findById(id);
        if (clienteOpt.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente no encontrado con id " + id);
        }

    }
}
