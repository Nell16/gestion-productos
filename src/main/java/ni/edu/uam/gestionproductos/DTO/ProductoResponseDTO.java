package ni.edu.uam.gestionproductos.DTO;

import ni.edu.uam.gestionproductos.Entity.Producto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductoResponseDTO {

    private Integer id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precioVenta;
    private int existencia;
    private CategoriaResponseDTO categoria;
    private ProveedorResponseDTO proveedor;
    private Set<EtiquetaResponseDTO> etiquetas;

    public static ProductoResponseDTO from(Producto producto) {
        if (producto == null) {
            return null;
        }
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setCodigo(producto.getCodigo());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioVenta(producto.getPrecioVenta());
        dto.setExistencia(producto.getExistencia());
        dto.setCategoria(CategoriaResponseDTO.from(producto.getCategoria()));
        dto.setProveedor(ProveedorResponseDTO.from(producto.getProveedor()));
        dto.setEtiquetas(producto.getEtiquetas().stream()
                .map(EtiquetaResponseDTO::from)
                .collect(Collectors.toSet()));
        return dto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
    }

    public ProveedorResponseDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorResponseDTO proveedor) {
        this.proveedor = proveedor;
    }

    public Set<EtiquetaResponseDTO> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<EtiquetaResponseDTO> etiquetas) {
        this.etiquetas = etiquetas;
    }
}
