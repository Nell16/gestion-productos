package ni.edu.uam.gestionproductos.DTO;

import jakarta.validation.constraints.NotBlank;

public class EtiquetaRequestDTO {

    @NotBlank
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
