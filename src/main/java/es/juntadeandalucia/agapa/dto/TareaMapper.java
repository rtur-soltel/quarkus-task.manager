package es.juntadeandalucia.agapa.dto;

import es.juntadeandalucia.agapa.jpa.Tarea;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TareaMapper {

    public TareaDTO toDTO(Tarea tarea) {
        if (tarea == null) {
            return null;
        }
        return new TareaDTO(tarea.id, tarea.titulo, tarea.descripcion, tarea.completada);
    }

    public List<TareaDTO> toDTOList(List<Tarea> tareas) {
        return tareas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Tarea toEntity(TareaDTO dto) {
        if (dto == null) {
            return null;
        }
        Tarea tarea = new Tarea();
        tarea.titulo = dto.titulo;
        tarea.descripcion = dto.descripcion;
        tarea.completada = dto.completada;
        return tarea;
    }
}
