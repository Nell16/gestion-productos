package ni.edu.uam.gestionproductos.Controllers;

import jakarta.validation.Valid;
import ni.edu.uam.gestionproductos.DTO.ProveedorRequestDTO;
import ni.edu.uam.gestionproductos.DTO.ProveedorResponseDTO;
import ni.edu.uam.gestionproductos.Services.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public List<ProveedorResponseDTO> listar() {
        return proveedorService.listar();
    }

    @GetMapping("/{id}")
    public ProveedorResponseDTO buscar(@PathVariable Integer id) {
        return proveedorService.buscarPorId(id);
    }

    @PostMapping
    public ProveedorResponseDTO guardar(@Valid @RequestBody ProveedorRequestDTO dto) {
        return proveedorService.guardar(dto);
    }

    @PutMapping("/{id}")
    public ProveedorResponseDTO actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProveedorRequestDTO dto) {
        return proveedorService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
