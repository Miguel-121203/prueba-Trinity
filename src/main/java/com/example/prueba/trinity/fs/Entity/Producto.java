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

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @Column(unique = true, length = 10)
    private String numeroCuenta;

    @NotNull
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

    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }

    private String generarNumeroCuenta(String prefijo) {
        return prefijo + String.format("%08d", (int) (Math.random() * 100000000));
    }
}
