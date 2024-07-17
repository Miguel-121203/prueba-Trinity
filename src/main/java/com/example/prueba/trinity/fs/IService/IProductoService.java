package com.example.prueba.trinity.fs.IService;

import com.example.prueba.trinity.fs.Entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    void update(Producto producto, Long id);

    void delete(Long id);

}
