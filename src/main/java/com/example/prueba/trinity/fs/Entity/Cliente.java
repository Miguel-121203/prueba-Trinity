package com.example.prueba.trinity.fs.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoId;

    private int numId;

    private String nombre;

    private String apellido;

    private String correo;

    private LocalDate fechaNacimiento;

    private LocalDate fechaCreacion;

    private LocalDate fechaModificacion;


}
