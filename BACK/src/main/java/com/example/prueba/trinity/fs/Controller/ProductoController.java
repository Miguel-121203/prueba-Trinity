package com.example.prueba.trinity.fs.Controller;

import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.IService.IProductoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

        @PutMapping("/activar/{id}")
        public ResponseEntity<String> activateProducto(@PathVariable Long id) {
            try {
                service.activateProducto(id);
                return ResponseEntity.ok("Producto activado exitosamente");
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }
        }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<String> deactivateProducto(@PathVariable Long id) {
        try {
            service.deactivateProducto(id);
            return ResponseEntity.ok("Producto desactivado exitosamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelateProducto(@PathVariable Long id) {
        try {
            service.cancelateProducto(id);
            return ResponseEntity.ok("Producto cancelado exitosamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
