package com.example.prueba.trinity.fs.Service;

import com.example.prueba.trinity.fs.Dto.TransaccionRequestDto;
import com.example.prueba.trinity.fs.Entity.Enum.TipoTransaccion;
import com.example.prueba.trinity.fs.Entity.Producto;
import com.example.prueba.trinity.fs.Entity.Transaccion;
import com.example.prueba.trinity.fs.IRepository.IProductoRepository;
import com.example.prueba.trinity.fs.IRepository.ITransaccionRepository;
import com.example.prueba.trinity.fs.IService.ITransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransaccionService implements ITransaccionService {

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private ITransaccionRepository transaccionRepository;


    // ACA SE REALIZA EL SERVICIO PARA LA CONSIGNACION EL CUAL MIRAMOS SI ES PRODUCTO O CUENTA ESTA PRESENTE Y TRAEMOS EL SALDO
    // Y A ESE MISMO LE INGRESAMOS EL SALGO DESEADO
    @Override
    @Transactional
    public void consignacion(TransaccionRequestDto transaccionRequestDto) {
        Optional<Producto> productoOpt = productoRepository.findById(transaccionRequestDto.getProductoId());
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setSaldo(producto.getSaldo() + transaccionRequestDto.getMonto());
            productoRepository.save(producto);

            Transaccion transaccion = new Transaccion();
            transaccion.setTipoTransaccion(TipoTransaccion.CONSIGNACION);
            transaccion.setMonto(transaccionRequestDto.getMonto());
            transaccion.setProductoDestino(producto);
            transaccion.setProductoOrigen(producto);
            transaccionRepository.save(transaccion);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }


    // SERVICIO PARA RETIRAR SALDO DE UNA CUENTA, TRAEMOS EL PRODUCTO Y DEL SALDO RESTAMOS LO QUE QUEREMOS RETIRAR
    @Override
    @Transactional
    public void retiro(TransaccionRequestDto transaccionRequestDto) {
        Optional<Producto> productoOpt = productoRepository.findById(transaccionRequestDto.getProductoId());
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (producto.getSaldo() >= transaccionRequestDto.getMonto()) {
                producto.setSaldo(producto.getSaldo() - transaccionRequestDto.getMonto());
                productoRepository.save(producto);

                Transaccion transaccion = new Transaccion();
                transaccion.setTipoTransaccion(TipoTransaccion.RETIRO);
                transaccion.setMonto(transaccionRequestDto.getMonto());
                transaccion.setProductoOrigen(producto);
                transaccionRepository.save(transaccion);
            } else {
                throw new RuntimeException("Saldo insuficiente");
            }
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }

    // ACA HACEMOS LA TRANSFERENCIA
    @Override
    @Transactional
    public void transferencia(TransaccionRequestDto transaccionRequestDto) {
        Optional<Producto> productoOrigenOpt = productoRepository.findById(transaccionRequestDto.getProductoOrigenId());
        Optional<Producto> productoDestinoOpt = productoRepository.findById(transaccionRequestDto.getProductoDestinoId());

        if (productoOrigenOpt.isPresent() && productoDestinoOpt.isPresent()) {
            Producto productoOrigen = productoOrigenOpt.get();
            Producto productoDestino = productoDestinoOpt.get();

            if (productoOrigen.getSaldo() >= transaccionRequestDto.getMonto()) {
                // Descontar el monto de la cuenta origen
                productoOrigen.setSaldo(productoOrigen.getSaldo() - transaccionRequestDto.getMonto());
                productoRepository.save(productoOrigen);

                // Agregar el monto a la cuenta destino
                productoDestino.setSaldo(productoDestino.getSaldo() + transaccionRequestDto.getMonto());
                productoRepository.save(productoDestino);
            } else {
                throw new RuntimeException("Saldo insuficiente en la cuenta origen");
            }
        } else {
            throw new RuntimeException("Producto origen o destino no encontrado");
        }
    }
}
