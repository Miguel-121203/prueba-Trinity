package com.example.prueba.trinity.fs.Entity;

import com.example.prueba.trinity.fs.Entity.Enum.TipoTransaccion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;

    @NotNull
    private Double monto;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_origen_id")
    private Producto productoOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_destino_id")
    private Producto productoDestino;
}
