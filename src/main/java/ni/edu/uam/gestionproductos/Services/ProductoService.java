package ni.edu.uam.gestionproductos.Services;

import ni.edu.uam.gestionproductos.DTO.ProductoRequestDTO;
import ni.edu.uam.gestionproductos.DTO.ProductoResponseDTO;
import ni.edu.uam.gestionproductos.Entity.Categoria;
import ni.edu.uam.gestionproductos.Entity.Etiqueta;
import ni.edu.uam.gestionproductos.Entity.Producto;
import ni.edu.uam.gestionproductos.Repositories.CategoriaRepository;
import ni.edu.uam.gestionproductos.Repositories.EtiquetaRepository;
import ni.edu.uam.gestionproductos.Repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final EtiquetaRepository etiquetaRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            EtiquetaRepository etiquetaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.etiquetaRepository = etiquetaRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll().stream()
                .map(ProductoResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Integer id) {
        return ProductoResponseDTO.from(obtener(id));
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        aplicarDatos(producto, dto);
        return ProductoResponseDTO.from(productoRepository.save(producto));
    }

    public ProductoResponseDTO actualizar(Integer id, ProductoRequestDTO dto) {
        Producto producto = obtener(id);
        aplicarDatos(producto, dto);
        return ProductoResponseDTO.from(productoRepository.save(producto));
    }

    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarPorCategoria(Integer categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId).stream()
                .map(ProductoResponseDTO::from)
                .toList();
    }

    @Transactional
    public ProductoResponseDTO agregarEtiqueta(Integer productoId, Integer etiquetaId) {
        Producto producto = obtener(productoId);
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId)
                .orElseThrow(() ->
                        new RuntimeException("Etiqueta no encontrada"));
        producto.getEtiquetas().add(etiqueta);
        return ProductoResponseDTO.from(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponseDTO quitarEtiqueta(Integer productoId, Integer etiquetaId) {
        Producto producto = obtener(productoId);
        producto.getEtiquetas().removeIf(etiqueta -> etiqueta.getId().equals(etiquetaId));
        return ProductoResponseDTO.from(productoRepository.save(producto));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarPorEtiqueta(Integer etiquetaId) {
        return productoRepository.findByEtiquetasId(etiquetaId).stream()
                .map(ProductoResponseDTO::from)
                .toList();
    }

    private Producto obtener(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));
    }

    private void aplicarDatos(Producto producto, ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new RuntimeException("Categoría no encontrada"));

        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setExistencia(dto.getExistencia());
        producto.setCategoria(categoria);
    }
}
