package es.juntadeandalucia.agapa.repository;

import es.juntadeandalucia.agapa.jpa.Tarea;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TareaRepository implements PanacheRepository<Tarea> {

}
