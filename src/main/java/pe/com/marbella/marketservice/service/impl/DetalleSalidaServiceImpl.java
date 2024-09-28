package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.model.DetalleEntrada;
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
    public DetalleSalida save(DetalleSalida detalleSalida) throws Exception {
        try {
            Long idProducto = detalleSalida.getProducto();

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

            producto.setStockActual(producto.getStockActual() - detalleSalida.getCantidad());

            productoRepository.save(producto);
            return detalleSalidaRepository.save(detalleSalida);
        }catch (Exception e){
            throw new Exception(e);
        }

    }

    @Override
    @Transactional
    public void delete(Long idSalida) throws Exception {
        try {
            List<DetalleSalida> detallesSalida = detalleSalidaRepository.findDetalleSalidaBySalidaAndEstado(idSalida,true);
            for (DetalleSalida detalleSalida : detallesSalida){
                Long idProducto = detalleSalida.getProducto();
                Producto producto = productoRepository.findById(idProducto)
                        .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

                producto.setStockActual(producto.getStockActual() + detalleSalida.getCantidad());
                detalleSalida.setEstado(false);
                detalleSalidaRepository.save(detalleSalida);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
