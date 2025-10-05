package com.example.prueba.trinity.fs.IRepository;

import com.example.prueba.trinity.fs.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Long> {

    List<Producto> findByActivoTrue();

    Optional<Producto> findByIdAndActivoTrue(Long id);
}
