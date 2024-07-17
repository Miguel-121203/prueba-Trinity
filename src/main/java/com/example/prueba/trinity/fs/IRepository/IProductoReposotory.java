package com.example.prueba.trinity.fs.IRepository;

import com.example.prueba.trinity.fs.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoReposotory extends JpaRepository<Producto,Long> {
}
