package ni.edu.uam.gestionproductos.Controllers;

import ni.edu.uam.gestionproductos.DTO.ProductoRequestDTO;
import ni.edu.uam.gestionproductos.DTO.ProductoResponseDTO;
import ni.edu.uam.gestionproductos.Services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoResponseDTO> listar() {
        return productoService.listar();
    }

    @GetMapping("/{id}")
    public ProductoResponseDTO buscar(@PathVariable Integer id) {
        return productoService.buscarPorId(id);
    }

    @PostMapping
    public ProductoResponseDTO guardar(@RequestBody ProductoRequestDTO dto) {
        return productoService.guardar(dto);
    }

    @PutMapping("/{id}")
    public ProductoResponseDTO actualizar(
            @PathVariable Integer id,
            @RequestBody ProductoRequestDTO dto) {
        return productoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<ProductoResponseDTO> listarPorCategoria(@PathVariable Integer categoriaId) {
        return productoService.listarPorCategoria(categoriaId);
    }

    @PostMapping("/{productoId}/etiquetas/{etiquetaId}")
    public ProductoResponseDTO agregarEtiqueta(
            @PathVariable Integer productoId,
            @PathVariable Integer etiquetaId) {
        return productoService.agregarEtiqueta(productoId, etiquetaId);
    }

    @DeleteMapping("/{productoId}/etiquetas/{etiquetaId}")
    public ResponseEntity<Void> quitarEtiqueta(
            @PathVariable Integer productoId,
            @PathVariable Integer etiquetaId) {
        productoService.quitarEtiqueta(productoId, etiquetaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/etiqueta/{etiquetaId}")
    public List<ProductoResponseDTO> listarPorEtiqueta(@PathVariable Integer etiquetaId) {
        return productoService.listarPorEtiqueta(etiquetaId);
    }
}
