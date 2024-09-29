package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.exception.InsufficientStockException;
import pe.com.marbella.marketservice.model.DetalleEntrada;
import pe.com.marbella.marketservice.model.Producto;
import pe.com.marbella.marketservice.repository.DetalleEntradaRepository;
import pe.com.marbella.marketservice.repository.ProductoRepository;
import pe.com.marbella.marketservice.service.DetalleEntradaService;

import java.util.List;

@Service
public class DetalleEntradaServiceImpl implements DetalleEntradaService {
    @Autowired
    DetalleEntradaRepository detalleEntradaRepository;
    @Autowired
    ProductoRepository productoRepository;

    @Override
    @Transactional
    public DetalleEntrada save(DetalleEntrada detalleEntrada) throws Exception{
        Long idProducto = detalleEntrada.getProducto();

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

        producto.setStockActual(producto.getStockActual() + detalleEntrada.getCantidad());
        productoRepository.save(producto);

        return detalleEntradaRepository.save(detalleEntrada);
    }

    @Override
    @Transactional
    public void delete(Long idEntrada) throws Exception{
        List<DetalleEntrada> detallesEntrada = detalleEntradaRepository.findDetalleEntradaByEntradaAndEstado(idEntrada, true);

        for (DetalleEntrada detalleEntrada : detallesEntrada) {
            Long idProducto = detalleEntrada.getProducto();

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

            int stockActual = producto.getStockActual();
            int cantidadEntrada = detalleEntrada.getCantidad();
            if (stockActual - cantidadEntrada < 0) {
                throw new InsufficientStockException("Stock insuficiente: El stock del producto '" + producto.getNombrePro() +
                        "' es "+stockActual+", no se pueden retirar "+cantidadEntrada+" unidades.");
            }

            producto.setStockActual(stockActual - cantidadEntrada);
            productoRepository.save(producto);

            detalleEntrada.setEstado(false);
            detalleEntradaRepository.save(detalleEntrada);
        }
    }
}
