package com.example.prueba.trinity.fs.IRepository;

import com.example.prueba.trinity.fs.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente,Long> {
}
