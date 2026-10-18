package gt.carritogt.repository;

import gt.carritogt.entity.ItemCarrito;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {

    List<ItemCarrito> findByCodUsuario(String codUsuario);

    Optional<ItemCarrito> findByCodUsuarioAndProductoIdProducto(String codUsuario, Integer idProducto);

    void deleteByCodUsuario(String codUsuario);
}
