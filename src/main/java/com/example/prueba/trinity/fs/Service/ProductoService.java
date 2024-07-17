package com.example.prueba.trinity.fs.Service;

import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.IService.IProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {
    @Override
    public List<Producto> findAll() {
        return List.of();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Producto save(Producto producto) {
        return null;
    }

    @Override
    public void update(Producto producto, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void activateProducto(Long id) {

    }

    @Override
    public void desactivateProducto(Long id) {

    }
}
