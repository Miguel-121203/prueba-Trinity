package com.example.prueba.trinity.fs.Controller;

import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.IService.IProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/producto")
public class ProductoController {

    @Autowired
    private IProductoService service;


    @GetMapping()
    public ResponseEntity<List<Producto>> findAll() {
        List<Producto> productos = service.findAll();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Producto> save(@Valid @RequestBody Producto producto) {
        Producto newProducto = service.save(producto);
        return ResponseEntity.ok(newProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @Valid @RequestBody Producto productoDetails) {
        Producto updatedProducto = service.update(productoDetails, id);
        return ResponseEntity.ok(updatedProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        service.activateProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/deactivar")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.desactivateProducto(id);
        return ResponseEntity.noContent().build();
    }
}
