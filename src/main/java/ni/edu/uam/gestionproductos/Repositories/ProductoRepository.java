package ni.edu.uam.gestionproductos.Repositories;

import ni.edu.uam.gestionproductos.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Integer> {
}