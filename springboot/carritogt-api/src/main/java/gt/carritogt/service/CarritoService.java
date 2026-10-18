package gt.carritogt.service;

import gt.carritogt.entity.ItemCarrito;
import gt.carritogt.entity.Producto;
import gt.carritogt.repository.ItemCarritoRepository;
import gt.carritogt.repository.ProductoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarritoService {

    private final ItemCarritoRepository itemCarritoRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(ItemCarritoRepository itemCarritoRepository, ProductoRepository productoRepository) {
        this.itemCarritoRepository = itemCarritoRepository;
        this.productoRepository = productoRepository;
    }

    public List<ItemCarrito> listar(String codUsuario) {
        return itemCarritoRepository.findByCodUsuario(codUsuario);
    }

    public ItemCarrito agregar(String codUsuario, Integer idProducto, Integer cantidad) {
        if (cantidad == null || cantidad < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser 1 o más");
        }
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe el producto " + idProducto));

        ItemCarrito item = itemCarritoRepository
                .findByCodUsuarioAndProductoIdProducto(codUsuario, idProducto)
                .orElse(null);

        if (item == null) {
            item = new ItemCarrito();
            item.setCodUsuario(codUsuario);
            item.setProducto(producto);
            item.setCantidad(cantidad);
        } else {
            item.setCantidad(item.getCantidad() + cantidad);
        }
        return itemCarritoRepository.save(item);
    }


    public void eliminar(Integer idShoppingCart) {
        if (!itemCarritoRepository.existsById(idShoppingCart)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No existe el renglón " + idShoppingCart);
        }
        itemCarritoRepository.deleteById(idShoppingCart);
    }

    @Transactional
    public void vaciar(String codUsuario) {
        itemCarritoRepository.deleteByCodUsuario(codUsuario);
    }

    @Override
    public String toString() {
        return "CarritoService";
    }
}
