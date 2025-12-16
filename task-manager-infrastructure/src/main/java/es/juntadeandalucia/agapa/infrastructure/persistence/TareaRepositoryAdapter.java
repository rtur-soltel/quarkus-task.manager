package es.juntadeandalucia.agapa.infrastructure.persistence;

import es.juntadeandalucia.agapa.domain.model.Tarea;
import es.juntadeandalucia.agapa.domain.repository.TareaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador que implementa el puerto del repositorio
 * Traduce entre el modelo de dominio y la capa de persistencia
 */
@ApplicationScoped
public class TareaRepositoryAdapter implements TareaRepository {

    @Inject
    TareaPanacheRepository panacheRepository;

    @Override
    public List<Tarea> findAll() {
        return panacheRepository.listAll().stream()
                .map(TareaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tarea> findById(Long id) {
        TareaEntity entity = panacheRepository.findById(id);
        return entity != null ? Optional.of(entity.toDomain()) : Optional.empty();
    }

    @Override
    public Optional<Tarea> findByTitulo(String titulo) {
        TareaEntity entity = panacheRepository.findByTitulo(titulo);
        return entity != null ? Optional.of(entity.toDomain()) : Optional.empty();
    }

    @Override
    public Tarea save(Tarea tarea) {
        TareaEntity entity = TareaEntity.fromDomain(tarea);
        if (entity.getId() == null) {
            panacheRepository.persist(entity);
        } else {
            entity = panacheRepository.getEntityManager().merge(entity);
        }
        return entity.toDomain();
    }

    @Override
    public void deleteById(Long id) {
        panacheRepository.deleteById(id);
    }

    @Override
    public boolean existsByTitulo(String titulo) {
        return panacheRepository.findByTitulo(titulo) != null;
    }
}
