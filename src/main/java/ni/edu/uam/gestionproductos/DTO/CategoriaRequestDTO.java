package ni.edu.uam.gestionproductos.DTO;

public class CategoriaRequestDTO {

    private String nombre;
    private boolean activa = true;

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
