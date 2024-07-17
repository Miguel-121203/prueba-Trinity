package com.example.prueba.trinity.fs.Controller;

import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.IService.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/producto")
public class ProductoController {

    @Autowired
    private IProductoService service;


    @GetMapping()
    public List<Producto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Producto> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public Producto save(@RequestBody Producto producto) {
        return service.save(Producto);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Producto producto, @PathVariable Long id) {
        service.update(Producto, id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
