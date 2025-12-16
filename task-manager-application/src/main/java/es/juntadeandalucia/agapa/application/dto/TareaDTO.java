package es.juntadeandalucia.agapa.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para transferencia de datos de Tarea
 */
public class TareaDTO {

    public Long id;

    @NotBlank(message = "El título no puede estar vacío")
    public String titulo;

    public String descripcion;

    @NotNull(message = "El estado de completada no puede ser nulo")
    public Boolean completada;

    public TareaDTO() {
        this.completada = false;
    }

    public TareaDTO(Long id, String titulo, String descripcion, Boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada != null ? completada : false;
    }
}
