package gt.carritogt.controller;

import gt.carritogt.entity.ItemCarrito;
import gt.carritogt.service.CarritoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{codUsuario}")
    public List<ItemCarrito> listar(@PathVariable String codUsuario) {
        return carritoService.listar(codUsuario);
    }

    @PostMapping("/{codUsuario}")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemCarrito agregar(@PathVariable String codUsuario, @RequestBody AgregarRequest datos) {
        return carritoService.agregar(codUsuario, datos.getIdProducto(), datos.getCantidad());
    }

    @DeleteMapping("/item/{idShoppingCart}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer idShoppingCart) {
        carritoService.eliminar(idShoppingCart);
    }

    @DeleteMapping("/{codUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vaciar(@PathVariable String codUsuario) {
        carritoService.vaciar(codUsuario);
    }

    @Override
    public String toString() {
        return "CarritoController";
    }
}
