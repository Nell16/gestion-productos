package ni.edu.uam.gestionproductos.Repositories;

import ni.edu.uam.gestionproductos.Entity.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtiquetaRepository
        extends JpaRepository<Etiqueta, Integer> {
}
