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

/**
 * Implementación de la interfaz DetalleSalidaService.
 * Proporciona métodos para gestionar los detalles de cada salida.
 */
@Service
public class DetalleSalidaServiceImpl implements DetalleSalidaService {
    @Autowired
    DetalleSalidaRepository detalleSalidaRepository;
    @Autowired
    ProductoRepository productoRepository;

    /**
     * Guarda un nuevo detalle de salida y actualiza el stock del producto asociado.
     *
     * @param detalleSalida El detalle de salida a guardar.
     * @return El detalle de salida guardado.
     * @throws Exception Si ocurre un error al guardar el detalle de salida.
     */
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

    /**
     * Elimina los detalles de salida asociados a una salida y actualiza el stock de los productos asociados.
     *
     * @param idSalida El ID de la salida a la que pertenecen los detalles de salida.
     * @throws Exception Si ocurre un error al eliminar los detalles de salida.
     */
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
