package ni.edu.uam.gestionproductos.Services;

import ni.edu.uam.gestionproductos.DTO.CategoriaRequestDTO;
import ni.edu.uam.gestionproductos.DTO.CategoriaResponseDTO;
import ni.edu.uam.gestionproductos.Entity.Categoria;
import ni.edu.uam.gestionproductos.Repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaResponseDTO::from)
                .toList();
    }

    public CategoriaResponseDTO buscarPorId(Integer id) {
        return CategoriaResponseDTO.from(obtener(id));
    }

    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        aplicarDatos(categoria, dto);
        return CategoriaResponseDTO.from(categoriaRepository.save(categoria));
    }

    public CategoriaResponseDTO actualizar(Integer id, CategoriaRequestDTO dto) {
        Categoria categoria = obtener(id);
        aplicarDatos(categoria, dto);
        return CategoriaResponseDTO.from(categoriaRepository.save(categoria));
    }

    public void eliminar(Integer id) {
        categoriaRepository.deleteById(id);
    }

    private Categoria obtener(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    private void aplicarDatos(Categoria categoria, CategoriaRequestDTO dto) {
        categoria.setNombre(dto.getNombre());
        categoria.setActiva(dto.isActiva());
    }
}
