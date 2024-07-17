package com.example.prueba.trinity.fs.Entity;

import com.example.prueba.trinity.fs.Entity.Enum.TipoTransaccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movimientos_financieros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transacciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransaccion tipoTransaccion;
}
