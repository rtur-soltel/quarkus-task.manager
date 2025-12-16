package es.juntadeandalucia.agapa.domain.model;

/**
 * Entidad de dominio: Tarea
 * Representa el modelo de negocio puro sin dependencias de infraestructura
 */
public class Tarea {

    private Long id;
    private String titulo;
    private String descripcion;
    private boolean completada;

    // Constructor por defecto
    public Tarea() {
        this.completada = false;
    }

    // Constructor con parámetros
    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = false;
    }

    // Constructor completo
    public Tarea(Long id, String titulo, String descripcion, boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
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

    // Método de negocio
    public void marcarComoCompletada() {
        this.completada = true;
    }

    // Validación de negocio
    public boolean isValida() {
        return titulo != null && !titulo.trim().isEmpty();
    }
}
