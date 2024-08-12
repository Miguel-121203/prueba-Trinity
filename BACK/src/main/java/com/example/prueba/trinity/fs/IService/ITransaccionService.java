package com.example.prueba.trinity.fs.IService;


import com.example.prueba.trinity.fs.Dto.TransaccionRequestDto;

public interface ITransaccionService {


    void consignacion(TransaccionRequestDto transaccionRequestDto);

    void retiro(TransaccionRequestDto transaccionRequestDto);

    void transferencia(TransaccionRequestDto transaccionRequestDto);
}
