package com.example.prueba.trinity.fs.Service;

import com.example.prueba.trinity.fs.Entity.Enum.EstadoCuenta;
import com.example.prueba.trinity.fs.Entity.Enum.TipoCuenta;
import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.IRepository.IProductoRepository;
import com.example.prueba.trinity.fs.IService.IProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {


    @Autowired
    private IProductoRepository repository;

    @Override
    public List<Producto> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Producto save(Producto producto) {
        if (producto.getTipoCuenta() == TipoCuenta.AHORRO && producto.getSaldo() < 0) {
            throw new IllegalArgumentException("La cuenta de ahorro no puede tener un saldo menor a $0");
        }

        producto.setNumeroCuenta(generarNumeroCuenta(producto.getTipoCuenta()));

        if (producto.getTipoCuenta() == TipoCuenta.AHORRO) {
            producto.setEstadoCuenta(EstadoCuenta.ACTIVA);
        }

        return repository.save(producto);
    }



    @Override
    public Producto update(Producto producto, Long id) {
        Optional<Producto> productoOpt = repository.findById(id);
        if (productoOpt.isPresent()) {
            Producto productos = productoOpt.get();
            productos.setTipoCuenta(productos.getTipoCuenta());
            productos.setEstadoCuenta(productos.getEstadoCuenta());
            productos.setSaldo(productos.getSaldo());
            productos.setExentaGmf(productos.getExentaGmf());
            productos.setFechaModificacion(LocalDateTime.now());
            return repository.save(productos);
        } else {
            throw new RuntimeException("Producto no encontrado con id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void activateProducto(Long id) {
        Optional<Producto> productoOpt = repository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setEstadoCuenta(EstadoCuenta.ACTIVA);
            producto.setFechaModificacion(LocalDateTime.now());
            repository.save(producto);
        } else {
            throw new EntityNotFoundException("Producto no encontrado con id " + id);
        }
    }

    @Override
    public void deactivateProducto(Long id) {
        Optional<Producto> productoOpt = repository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setEstadoCuenta(EstadoCuenta.INACTIVA);
            producto.setFechaModificacion(LocalDateTime.now());
            repository.save(producto);
        } else {
            throw new EntityNotFoundException("Producto no encontrado con id " + id);
        }
    }
    @Override
    public void cancelateProducto(Long id) {
        Optional<Producto> productoOpt = repository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (producto.getSaldo() != 0) {
                throw new IllegalStateException("No se puede cancelar una cuenta con saldo diferente a 0");
            }
            producto.setEstadoCuenta(EstadoCuenta.CANCELADA);
            producto.setFechaModificacion(LocalDateTime.now());
            repository.save(producto);
        } else {
            throw new EntityNotFoundException("Producto no encontrado con id " + id);
        }
    }

    private String generarNumeroCuenta(TipoCuenta tipoCuenta) {
        String prefix = tipoCuenta == TipoCuenta.AHORRO ? "53" : "33";
        return prefix + String.format("%08d", (int) (Math.random() * 100000000));
    }
}
