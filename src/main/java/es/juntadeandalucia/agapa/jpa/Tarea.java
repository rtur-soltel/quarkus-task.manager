package es.juntadeandalucia.agapa.jpa;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarea_seq_gen")
    @SequenceGenerator(name = "tarea_seq_gen", sequenceName = "tarea_seq", allocationSize = 1)
    public Long id;

    @Column(nullable = false)
    public String titulo;

    @Column
    public String descripcion;

    @Column(nullable = false)
    public boolean completada = false;

    public Tarea() {
    }

    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = false;
    }
}
