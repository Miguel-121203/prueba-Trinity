package com.example.prueba.trinity.fs.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de identificacion debe ser obligatorio")
    private String tipoId;

    @NotNull(message = "Numero de identificacion debe ser obligatorio")
    private int numId;

    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Correo debe ser valido y contar con su respectivo formato")
    @NotBlank(message = "El correo electronico es obligatorio")
    private String correo;

    @Past(message = "La fecha de nacimiento debe de contar con fecha del pasado no con fechas futuras")
    private LocalDate fechaNacimiento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column()
    private LocalDateTime fechaModificacion;

    //INICIALIZAMOS ESTOS DATOS PARA CUMPLIR LOS REQUERIMIENTOS
    // EL PREPERSIST SIRVE PARA INICIALIZAR LOS DATOS ANTES DE CREAR LA PERSISTENCIA EN LA BASE DE DATOS
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    // EN ESTA ACTUALIZAMOS LA FECHA DE MODIFICACION JUSTO ANTES DE HACER LA PERSISTENCIA DE NUEVO EN LA BASE DE DATOS
    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }



}
