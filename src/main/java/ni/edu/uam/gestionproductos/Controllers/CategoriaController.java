package ni.edu.uam.gestionproductos.Controllers;

import ni.edu.uam.gestionproductos.DTO.CategoriaRequestDTO;
import ni.edu.uam.gestionproductos.DTO.CategoriaResponseDTO;
import ni.edu.uam.gestionproductos.Services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponseDTO> listar() {
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public CategoriaResponseDTO buscar(@PathVariable Integer id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    public CategoriaResponseDTO guardar(@RequestBody CategoriaRequestDTO dto) {
        return categoriaService.guardar(dto);
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO actualizar(
            @PathVariable Integer id,
            @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
