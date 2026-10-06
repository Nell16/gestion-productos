package ni.edu.uam.gestionproductos.Repositories;

import ni.edu.uam.gestionproductos.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository
        extends JpaRepository<Producto, Integer> {

    List<Producto> findByCategoriaId(Integer categoriaId);

    List<Producto> findByEtiquetasId(Integer etiquetaId);
}
