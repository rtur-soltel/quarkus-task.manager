package es.juntadeandalucia.agapa.application.service;

import es.juntadeandalucia.agapa.domain.model.Tarea;
import es.juntadeandalucia.agapa.domain.repository.TareaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para gestión de tareas (casos de uso)
 */
@ApplicationScoped
public class TareasService {

    @Inject
    TareaRepository tareaRepository;

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id);
    }

    @Transactional
    public Tarea crearTarea(Tarea tarea) {
        // Validación de negocio
        if (!tarea.isValida()) {
            throw new IllegalArgumentException("La tarea no es válida: el título es obligatorio");
        }

        // Verificar que no exista una tarea con el mismo título
        if (tareaRepository.existsByTitulo(tarea.getTitulo())) {
            throw new IllegalArgumentException("Ya existe una tarea con el título: " + tarea.getTitulo());
        }

        return tareaRepository.save(tarea);
    }

    @Transactional
    public Optional<Tarea> marcarComoCompletada(Long id) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.marcarComoCompletada();
            return Optional.of(tareaRepository.save(tarea));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean eliminarTarea(Long id) {
        if (tareaRepository.findById(id).isPresent()) {
            tareaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
