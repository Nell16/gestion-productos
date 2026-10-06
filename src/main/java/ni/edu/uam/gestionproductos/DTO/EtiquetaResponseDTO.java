package ni.edu.uam.gestionproductos.DTO;

import ni.edu.uam.gestionproductos.Entity.Etiqueta;

public class EtiquetaResponseDTO {

    private Integer id;
    private String nombre;

    public static EtiquetaResponseDTO from(Etiqueta etiqueta) {
        if (etiqueta == null) {
            return null;
        }
        EtiquetaResponseDTO dto = new EtiquetaResponseDTO();
        dto.setId(etiqueta.getId());
        dto.setNombre(etiqueta.getNombre());
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
}
