package ni.edu.uam.gestionproductos.Controllers;

import ni.edu.uam.gestionproductos.Entity.Categoria;
import ni.edu.uam.gestionproductos.Repositories.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Categoria> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Categoria guardar(@RequestBody Categoria categoria) {
        return repository.save(categoria);
    }
}