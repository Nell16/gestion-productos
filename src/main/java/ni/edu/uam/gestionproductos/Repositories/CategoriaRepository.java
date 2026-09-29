package ni.edu.uam.gestionproductos.Repositories;

import ni.edu.uam.gestionproductos.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository
        extends JpaRepository<Categoria, Integer> {
}