package com.example.prueba.trinity.fs.Entity;

import com.example.prueba.trinity.fs.Entity.Enum.EstadoCuenta;
import com.example.prueba.trinity.fs.Entity.Enum.TipoCuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tipo de cuenta no puede estar vacio")
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @Column(unique = true, length = 10)
    private String numeroCuenta;

    @NotNull(message = "El estado de la cuenta no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private Double saldo;

    @NotNull
    private Boolean exentaGmf;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;


    //INICIALIZAMOS ESTOS DATOS PARA CUMPLIR LOS REQUERIMIENTOS
    // EL PREPERSIST SIRVE PARA INICIALIZAR LOS DATOS ANTES DE CREAR LA PERSISTENCIA EN LA BASE DE DATOS
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
        if (this.tipoCuenta == TipoCuenta.AHORRO) {
            this.estadoCuenta = EstadoCuenta.ACTIVA;
            this.numeroCuenta = generarNumeroCuenta("53");
        } else if (this.tipoCuenta == TipoCuenta.CORRIENTE) {
            this.numeroCuenta = generarNumeroCuenta("33");
        }
    }

    // EN ESTA ACTUALIZAMOS LA FECHA DE MODIFICACION JUSTO ANTES DE HACER LA PERSISTENCIA DE NUEVO EN LA BASE DE DATOS
    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }

    // ESTE METODO SIRVE PARA GENERAR EL NUMERO DE CUENTA EN AUTOMATICO
    private String generarNumeroCuenta(String prefijo) {
        return prefijo + String.format("%08d", (int) (Math.random() * 100000000));
    }

}
