package ni.edu.uam.gestionproductos.DTO;

import ni.edu.uam.gestionproductos.Entity.Categoria;

public class CategoriaResponseDTO {

    private Integer id;
    private String nombre;
    private boolean activa;

    public static CategoriaResponseDTO from(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setActiva(categoria.isActiva());
        return dto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
