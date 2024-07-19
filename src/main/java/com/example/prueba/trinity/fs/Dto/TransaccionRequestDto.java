package com.example.prueba.trinity.fs.Dto;

import lombok.Data;

@Data
public class TransaccionRequestDto {
    private Long productoId;
    private Long productoOrigenId;
    private Long productoDestinoId;
    private Double monto;
}
