package es.juntadeandalucia.agapa.domain.repository;

import es.juntadeandalucia.agapa.domain.model.Tarea;

import java.util.List;
import java.util.Optional;

/**
 * Puerto del repositorio (interfaz del dominio)
 * Define el contrato para la persistencia sin especificar la implementación
 */
public interface TareaRepository {

    List<Tarea> findAll();

    Optional<Tarea> findById(Long id);

    Optional<Tarea> findByTitulo(String titulo);

    Tarea save(Tarea tarea);

    void deleteById(Long id);

    boolean existsByTitulo(String titulo);
}
