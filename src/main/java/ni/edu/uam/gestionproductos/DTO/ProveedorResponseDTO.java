package ni.edu.uam.gestionproductos.DTO;

import ni.edu.uam.gestionproductos.Entity.Proveedor;

public class ProveedorResponseDTO {

    private Integer id;
    private String nombre;
    private String telefono;
    private String correo;
    private boolean activo;

    public static ProveedorResponseDTO from(Proveedor proveedor) {
        if (proveedor == null) {
            return null;
        }
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setTelefono(proveedor.getTelefono());
        dto.setCorreo(proveedor.getCorreo());
        dto.setActivo(proveedor.isActivo());
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
