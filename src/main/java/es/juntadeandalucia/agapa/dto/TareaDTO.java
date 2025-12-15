package es.juntadeandalucia.agapa.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Tarea", description = "Representa una tarea del sistema")
public class TareaDTO {

    @Schema(description = "Identificador único de la tarea", example = "1")
    public Long id;

    @Schema(description = "Título o nombre de la tarea", example = "Implementar endpoint REST", required = true)
    public String titulo;

    @Schema(description = "Descripción detallada de la tarea", example = "Crear un endpoint para obtener todas las tareas")
    public String descripcion;

    @Schema(description = "Estado de completación de la tarea", example = "false")
    public boolean completada;

    public TareaDTO() {
    }

    public TareaDTO(Long id, String titulo, String descripcion, boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
    }
}
