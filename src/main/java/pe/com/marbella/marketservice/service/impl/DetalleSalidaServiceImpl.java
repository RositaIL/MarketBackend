package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.exception.InsufficientStockException;
import pe.com.marbella.marketservice.model.DetalleSalida;
import pe.com.marbella.marketservice.model.Producto;
import pe.com.marbella.marketservice.repository.DetalleSalidaRepository;
import pe.com.marbella.marketservice.repository.ProductoRepository;
import pe.com.marbella.marketservice.service.DetalleSalidaService;

import java.util.List;

@Service
public class DetalleSalidaServiceImpl implements DetalleSalidaService {
    @Autowired
    DetalleSalidaRepository detalleSalidaRepository;
    @Autowired
    ProductoRepository productoRepository;

    @Override
    @Transactional
    public DetalleSalida save(DetalleSalida detalleSalida) throws Exception{
        Long idProducto = detalleSalida.getProducto();

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

        int stockActual = producto.getStockActual();
        int cantidadSalida = detalleSalida.getCantidad();
        if (stockActual - cantidadSalida < 0) {
            throw new InsufficientStockException("Stock insuficiente: El stock del producto '" + producto.getNombrePro() +
                    "' es "+stockActual+", no se pueden retirar "+cantidadSalida+" unidades. ");
        }

        producto.setStockActual(stockActual - cantidadSalida);
        productoRepository.save(producto);

        return detalleSalidaRepository.save(detalleSalida);
    }

    @Override
    @Transactional
    public void delete(Long idSalida) throws Exception{
        List<DetalleSalida> detallesSalida = detalleSalidaRepository.findDetalleSalidaBySalidaAndEstado(idSalida, true);

        for (DetalleSalida detalleSalida : detallesSalida) {
            Long idProducto = detalleSalida.getProducto();

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

            producto.setStockActual(producto.getStockActual() + detalleSalida.getCantidad());
            productoRepository.save(producto);

            detalleSalida.setEstado(false);
            detalleSalidaRepository.save(detalleSalida);
        }
    }
}
