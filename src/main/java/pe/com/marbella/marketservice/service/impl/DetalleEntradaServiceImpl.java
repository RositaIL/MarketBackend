package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.model.DetalleEntrada;
import pe.com.marbella.marketservice.model.Producto;
import pe.com.marbella.marketservice.repository.DetalleEntradaRepository;
import pe.com.marbella.marketservice.repository.ProductoRepository;

@Service
public class DetalleEntradaServiceImpl {
    @Autowired
    DetalleEntradaRepository detalleEntradaRepository;
    @Autowired
    ProductoRepository productoRepository;

    @Transactional
    public DetalleEntrada save(DetalleEntrada detalleEntrada) throws Exception {
        try {
            Long idProducto = detalleEntrada.getProducto();

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

            producto.setStockActual(producto.getStockActual() + detalleEntrada.getCantidad());

            productoRepository.save(producto);
            return detalleEntradaRepository.save(detalleEntrada);
        }catch (Exception e){
            throw new Exception("Error al guardar el detalle de entrada: " + e.getMessage());
        }

    }

    @Transactional
    public void delete(Long idDetalleEntrada) throws Exception {
        try {
            DetalleEntrada detalleEntrada = detalleEntradaRepository.findById(idDetalleEntrada)
                    .orElseThrow(() -> new EntityNotFoundException("El detalle de entrada con ID: " + idDetalleEntrada + " no existe"));

            Long idProducto = detalleEntrada.getProducto();
            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

            producto.setStockActual(producto.getStockActual() - detalleEntrada.getCantidad());

            detalleEntrada.setEstado(false);

            detalleEntradaRepository.save(detalleEntrada);
            productoRepository.save(producto);
        } catch (Exception e) {
            throw new Exception("Error al eliminar el detalle de entrada: " + e.getMessage());
        }
    }
}
