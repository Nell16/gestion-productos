package ni.edu.uam.gestionproductos.Services;

import ni.edu.uam.gestionproductos.DTO.ProveedorRequestDTO;
import ni.edu.uam.gestionproductos.DTO.ProveedorResponseDTO;
import ni.edu.uam.gestionproductos.Entity.Proveedor;
import ni.edu.uam.gestionproductos.Repositories.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<ProveedorResponseDTO> listar() {
        return proveedorRepository.findAll().stream()
                .map(ProveedorResponseDTO::from)
                .toList();
    }

    public ProveedorResponseDTO buscarPorId(Integer id) {
        return ProveedorResponseDTO.from(obtener(id));
    }

    public ProveedorResponseDTO guardar(ProveedorRequestDTO dto) {
        Proveedor proveedor = new Proveedor();
        aplicarDatos(proveedor, dto);
        return ProveedorResponseDTO.from(proveedorRepository.save(proveedor));
    }

    public ProveedorResponseDTO actualizar(Integer id, ProveedorRequestDTO dto) {
        Proveedor proveedor = obtener(id);
        aplicarDatos(proveedor, dto);
        return ProveedorResponseDTO.from(proveedorRepository.save(proveedor));
    }

    public void eliminar(Integer id) {
        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado");
        }
        proveedorRepository.deleteById(id);
    }

    private Proveedor obtener(Integer id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    private void aplicarDatos(Proveedor proveedor, ProveedorRequestDTO dto) {
        proveedor.setNombre(dto.getNombre());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setCorreo(dto.getCorreo());
        proveedor.setActivo(dto.isActivo());
    }
}
