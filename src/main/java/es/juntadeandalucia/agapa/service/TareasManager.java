package es.juntadeandalucia.agapa.service;

import es.juntadeandalucia.agapa.jpa.Tarea;
import es.juntadeandalucia.agapa.repository.TareaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TareasManager {

    @Inject
    TareaRepository tareaRepository;

    public List<Tarea> listarTareas() {
        return tareaRepository.listAll();
    }

    @Transactional
    public Tarea crearTarea(Tarea tarea) {
        tareaRepository.persist(tarea);
        return tarea;
    }

    @Transactional
    public Tarea marcarComoDone(Long id) {
        Tarea tarea = tareaRepository.findById(id);
        if (tarea != null) {
            tarea.completada = true;
            tareaRepository.persist(tarea);
        }
        return tarea;
    }

    @Transactional
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
