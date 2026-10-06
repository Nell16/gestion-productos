package ni.edu.uam.gestionproductos.Controllers;

import ni.edu.uam.gestionproductos.Entity.Producto;
import ni.edu.uam.gestionproductos.Repositories.ProductoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository repository;

    public ProductoController(ProductoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Producto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return repository.save(producto);
    }
}
