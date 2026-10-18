package gt.carritogt.service;

import gt.carritogt.entity.Producto;
import gt.carritogt.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public String toString() {
        return "ProductoService";
    }
}
