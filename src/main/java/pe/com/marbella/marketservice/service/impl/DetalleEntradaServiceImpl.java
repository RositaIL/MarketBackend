package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public DetalleEntrada save(DetalleEntrada detalleEntrada) throws Exception {
        try {
            Long idProducto = detalleEntrada.getProducto();

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

            producto.setStockActual(producto.getStockActual() + detalleEntrada.getCantidad());

            productoRepository.save(producto);
            return detalleEntradaRepository.save(detalleEntrada);
        }catch (Exception e){
            throw new Exception(e);
        }

    }

    @Override
    @Transactional
    public void delete(Long idEntrada) throws Exception {
        try {
            List<DetalleEntrada> detallesEntrada = detalleEntradaRepository.findDetalleEntradaByEntradaAndEstado(idEntrada, true);
            for (DetalleEntrada detalleEntrada : detallesEntrada) {
                Long idProducto = detalleEntrada.getProducto();
                Producto producto = productoRepository.findById(idProducto)
                        .orElseThrow(() -> new EntityNotFoundException("El producto con ID: " + idProducto + " no existe"));

                producto.setStockActual(producto.getStockActual() - detalleEntrada.getCantidad());
                detalleEntrada.setEstado(false);
                detalleEntradaRepository.save(detalleEntrada);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
