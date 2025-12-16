package es.juntadeandalucia.agapa.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repositorio Panache para TareaEntity
 */
@ApplicationScoped
public class TareaPanacheRepository implements PanacheRepository<TareaEntity> {

    public TareaEntity findByTitulo(String titulo) {
        return find("titulo", titulo).firstResult();
    }
}
