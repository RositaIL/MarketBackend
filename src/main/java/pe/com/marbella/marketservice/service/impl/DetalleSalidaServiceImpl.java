package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.model.DetalleSalida;
import pe.com.marbella.marketservice.model.Producto;
import pe.com.marbella.marketservice.repository.DetalleSalidaRepository;
import pe.com.marbella.marketservice.repository.ProductoRepository;

@Service
public class DetalleSalidaServiceImpl {
    @Autowired
    DetalleSalidaRepository detalleSalidaRepository;
    @Autowired
    ProductoRepository productoRepository;

    @Transactional
    public DetalleSalida save(DetalleSalida detalleSalida) throws Exception {
        try {
            Long idProducto = detalleSalida.getProducto();

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

            producto.setStockActual(producto.getStockActual() - detalleSalida.getCantidad());

            productoRepository.save(producto);
            return detalleSalidaRepository.save(detalleSalida);
        }catch (Exception e){
            throw new Exception("Error al guardar el detalle de salida: " + e.getMessage());
        }

    }

    @Transactional
    public void delete(Long idDetalleSalida) throws Exception {
        try {
            DetalleSalida detalleSalida = detalleSalidaRepository.findById(idDetalleSalida)
                    .orElseThrow(() -> new EntityNotFoundException("El detalle de entrada con ID: " + idDetalleSalida + " no existe"));

            Long idProducto = detalleSalida.getProducto();
            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

            producto.setStockActual(producto.getStockActual() + detalleSalida.getCantidad());

            detalleSalida.setEstado(false);

            detalleSalidaRepository.save(detalleSalida);
            productoRepository.save(producto);
        } catch (Exception e) {
            throw new Exception("Error al eliminar el detalle de entrada: " + e.getMessage());
        }
    }
}
