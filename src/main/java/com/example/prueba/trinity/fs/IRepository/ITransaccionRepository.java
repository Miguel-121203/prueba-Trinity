package com.example.prueba.trinity.fs.IRepository;

import com.example.prueba.trinity.fs.Entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransaccionRepository extends JpaRepository<Transaccion,Long> {
}
