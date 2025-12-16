package es.juntadeandalucia.agapa.application.mapper;

import es.juntadeandalucia.agapa.application.dto.TareaDTO;
import es.juntadeandalucia.agapa.domain.model.Tarea;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre entidades de dominio y DTOs
 */
@ApplicationScoped
public class TareaMapper {

    public TareaDTO toDTO(Tarea tarea) {
        if (tarea == null) {
            return null;
        }
        return new TareaDTO(
            tarea.getId(),
            tarea.getTitulo(),
            tarea.getDescripcion(),
            tarea.isCompletada()
        );
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
        tarea.setTitulo(dto.titulo);
        tarea.setDescripcion(dto.descripcion);
        tarea.setCompletada(dto.completada);
        return tarea;
    }

    public Tarea toEntityWithId(TareaDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Tarea(
            dto.id,
            dto.titulo,
            dto.descripcion,
            dto.completada
        );
    }
}
