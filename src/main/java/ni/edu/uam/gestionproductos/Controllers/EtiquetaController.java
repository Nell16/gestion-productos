package ni.edu.uam.gestionproductos.Controllers;

import jakarta.validation.Valid;
import ni.edu.uam.gestionproductos.DTO.EtiquetaRequestDTO;
import ni.edu.uam.gestionproductos.DTO.EtiquetaResponseDTO;
import ni.edu.uam.gestionproductos.Services.EtiquetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etiquetas")
public class EtiquetaController {

    private final EtiquetaService etiquetaService;

    public EtiquetaController(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }

    @GetMapping
    public List<EtiquetaResponseDTO> listar() {
        return etiquetaService.listar();
    }

    @GetMapping("/{id}")
    public EtiquetaResponseDTO buscar(@PathVariable Integer id) {
        return etiquetaService.buscarPorId(id);
    }

    @PostMapping
    public EtiquetaResponseDTO guardar(@Valid @RequestBody EtiquetaRequestDTO dto) {
        return etiquetaService.guardar(dto);
    }

    @PutMapping("/{id}")
    public EtiquetaResponseDTO actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EtiquetaRequestDTO dto) {
        return etiquetaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        etiquetaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
