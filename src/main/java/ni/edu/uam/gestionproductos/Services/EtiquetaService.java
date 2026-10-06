package ni.edu.uam.gestionproductos.Services;

import ni.edu.uam.gestionproductos.DTO.EtiquetaRequestDTO;
import ni.edu.uam.gestionproductos.DTO.EtiquetaResponseDTO;
import ni.edu.uam.gestionproductos.Entity.Etiqueta;
import ni.edu.uam.gestionproductos.Repositories.EtiquetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;

    public EtiquetaService(EtiquetaRepository etiquetaRepository) {
        this.etiquetaRepository = etiquetaRepository;
    }

    public List<EtiquetaResponseDTO> listar() {
        return etiquetaRepository.findAll().stream()
                .map(EtiquetaResponseDTO::from)
                .toList();
    }

    public EtiquetaResponseDTO buscarPorId(Integer id) {
        return EtiquetaResponseDTO.from(obtener(id));
    }

    public EtiquetaResponseDTO guardar(EtiquetaRequestDTO dto) {
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setNombre(dto.getNombre());
        return EtiquetaResponseDTO.from(etiquetaRepository.save(etiqueta));
    }

    public EtiquetaResponseDTO actualizar(Integer id, EtiquetaRequestDTO dto) {
        Etiqueta etiqueta = obtener(id);
        etiqueta.setNombre(dto.getNombre());
        return EtiquetaResponseDTO.from(etiquetaRepository.save(etiqueta));
    }

    public void eliminar(Integer id) {
        etiquetaRepository.deleteById(id);
    }

    private Etiqueta obtener(Integer id) {
        return etiquetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada"));
    }
}
