package ni.edu.uam.gestionproductos.Repositories;

import ni.edu.uam.gestionproductos.Entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}