package com.example.prueba.trinity.fs.IRepository;

import com.example.prueba.trinity.fs.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Long> {

    List<Cliente> findByActivoTrue();

    Optional<Cliente> findByIdAndActivoTrue(Long id);
}
