package com.example.prueba.trinity.fs.Service;

import com.example.prueba.trinity.fs.Entity.Cliente;
import com.example.prueba.trinity.fs.IRepository.IClienteRepository;
import com.example.prueba.trinity.fs.IService.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {


    @Autowired
    private IClienteRepository repository;

    // ENCONTRAR TODOS LOS CLIENTES
    @Override
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    @Override
    public Optional<Cliente> findById(Long id) {
        return repository.findById(id);
    }

    //GUARDAR O CREAR CLIENTE PERO ESTE NO DEBE SER MENOR DE EDAD POR ESO SE LE PONE UNA VALIDACION
    @Override
    public Cliente save(@Valid Cliente cliente) {
        if (!isOfAge(cliente.getFechaNacimiento())) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad.");
        }
        cliente.setFechaCreacion(LocalDateTime.now());
        cliente.setFechaModificacion(LocalDateTime.now());
        return repository.save(cliente);
    }

    //METODO DE VALIDACION PARA QUE EL CLIENTE SEA MAYOR DE EDAD
    private boolean isOfAge(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    //ACTUALIZAR
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

    //BORRAR O ELIMINAR
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
