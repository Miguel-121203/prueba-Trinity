package com.example.prueba.trinity.fs.IRepository;

import com.example.prueba.trinity.fs.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Long> {

    boolean existsByNumId(int numId);
}
