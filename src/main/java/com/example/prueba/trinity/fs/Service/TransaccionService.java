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

    @Override
    @Transactional
    public void transferencia(TransaccionRequestDto transaccionRequestDto) {
        Optional<Producto> productoOrigenOpt = productoRepository.findById(transaccionRequestDto.getProductoOrigenId());
        Optional<Producto> productoDestinoOpt = productoRepository.findById(transaccionRequestDto.getProductoDestinoId());

        if (productoOrigenOpt.isPresent() && productoDestinoOpt.isPresent()) {
            Producto productoOrigen = productoOrigenOpt.get();
            Producto productoDestino = productoDestinoOpt.get();

            if (productoOrigen.getSaldo() >= transaccionRequestDto.getMonto()) {
                // Debitar el monto de la cuenta origen
                productoOrigen.setSaldo(productoOrigen.getSaldo() - transaccionRequestDto.getMonto());
                productoRepository.save(productoOrigen);

                // Acreditar el monto a la cuenta destino
                productoDestino.setSaldo(productoDestino.getSaldo() + transaccionRequestDto.getMonto());
                productoRepository.save(productoDestino);

                // Crear transacción de débito
                Transaccion transaccionDebito = new Transaccion();
                transaccionDebito.setTipoTransaccion(TipoTransaccion.TRANSFERENCIA);
                transaccionDebito.setMonto(transaccionRequestDto.getMonto());
                transaccionDebito.setProductoOrigen(productoOrigen);
                transaccionDebito.setProductoDestino(productoDestino);
                transaccionRepository.save(transaccionDebito);

                // Crear transacción de crédito
                Transaccion transaccionCredito = new Transaccion();
                transaccionCredito.setTipoTransaccion(TipoTransaccion.TRANSFERENCIA);
                transaccionCredito.setMonto(transaccionRequestDto.getMonto());
                transaccionCredito.setProductoOrigen(productoOrigen);
                transaccionCredito.setProductoDestino(productoDestino);
                transaccionRepository.save(transaccionCredito);
            } else {
                throw new RuntimeException("Saldo insuficiente en la cuenta origen");
            }
        } else {
            throw new RuntimeException("Producto origen o destino no encontrado");
        }
    }
}
