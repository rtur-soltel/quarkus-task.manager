package es.juntadeandalucia.agapa.infrastructure.persistence;

import es.juntadeandalucia.agapa.domain.model.Tarea;
import jakarta.persistence.*;

/**
 * Entidad JPA para persistencia de Tarea
 * Adaptador de la entidad de dominio para la base de datos
 */
@Entity
@Table(name = "tarea")
public class TareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarea_seq_gen")
    @SequenceGenerator(name = "tarea_seq_gen", sequenceName = "tarea_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private boolean completada = false;

    public TareaEntity() {
    }

    public TareaEntity(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = false;
    }

    // Métodos para convertir desde/hacia el modelo de dominio
    public static TareaEntity fromDomain(Tarea tarea) {
        TareaEntity entity = new TareaEntity();
        entity.setId(tarea.getId());
        entity.setTitulo(tarea.getTitulo());
        entity.setDescripcion(tarea.getDescripcion());
        entity.setCompletada(tarea.isCompletada());
        return entity;
    }

    public Tarea toDomain() {
        return new Tarea(this.id, this.titulo, this.descripcion, this.completada);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
