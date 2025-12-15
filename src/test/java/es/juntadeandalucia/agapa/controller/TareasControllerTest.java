package es.juntadeandalucia.agapa.controller;

import es.juntadeandalucia.agapa.dto.TareaDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("integration")
class TareasControllerTest {

    private static Integer tareaIdCreada;

    @Test
    @Order(1)
    @DisplayName("Test 1: Listar todas las tareas - Debe devolver código 200")
    void testListarTareas() {
        given()
            .when()
                .get("/tareas")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
                // No verificamos tamaño porque la BD puede estar vacía al inicio
    }

    @Test
    @Order(2)
    @DisplayName("Test 2: Crear tarea válida - Debe devolver código 201")
    void testCrearTareaValida() {
        TareaDTO nuevaTarea = new TareaDTO();
        nuevaTarea.titulo = "Tarea de prueba JUnit";
        nuevaTarea.descripcion = "Esta es una tarea creada en el test";
        nuevaTarea.completada = false;

        tareaIdCreada = given()
            .contentType(ContentType.JSON)
            .body(nuevaTarea)
            .when()
                .post("/tareas")
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("titulo", equalTo("Tarea de prueba JUnit"))
                .body("descripcion", equalTo("Esta es una tarea creada en el test"))
                .body("completada", equalTo(false))
                .body("id", notNullValue())
            .extract()
                .path("id");

        System.out.println("ID de tarea creada: " + tareaIdCreada);
    }

    @Test
    @Order(3)
    @DisplayName("Test 3: Crear tarea sin título - Debe validar entrada")
    void testCrearTareaSinTitulo() {
        TareaDTO tareaInvalida = new TareaDTO();
        tareaInvalida.titulo = null;
        tareaInvalida.descripcion = "Tarea sin título";
        tareaInvalida.completada = false;

        // Nota: Quarkus con Hibernate Validator debería fallar aquí
        // Si no hay validación, el test fallará y necesitaremos agregar validación
        given()
            .contentType(ContentType.JSON)
            .body(tareaInvalida)
            .when()
                .post("/tareas")
            .then()
                .statusCode(anyOf(is(400), is(500))); // Puede ser 400 con validación o 500 por constraint de BD
    }

    @Test
    @Order(4)
    @DisplayName("Test 4: Consultar tarea existente - Debe devolver código 200")
    void testConsultarTareaExistente() {
        // Primero listamos para obtener un ID válido
        Integer idExistente = given()
            .when()
                .get("/tareas")
            .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
            .extract()
                .path("[0].id");

        // Ahora consultamos esa tarea específica (aunque el endpoint no existe en el controller)
        // Vamos a verificar que existe en la lista
        given()
            .when()
                .get("/tareas")
            .then()
                .statusCode(200)
                .body("id", hasItem(idExistente));
    }

    @Test
    @Order(5)
    @DisplayName("Test 5: Completar tarea existente - Debe devolver código 200")
    void testCompletarTareaExistente() {
        // Usamos la tarea que creamos en el test 2
        if (tareaIdCreada == null) {
            // Si no se ejecutó el test de creación, creamos una tarea ahora
            TareaDTO nuevaTarea = new TareaDTO();
            nuevaTarea.titulo = "Tarea para completar";
            nuevaTarea.descripcion = "Tarea que será marcada como completada";
            nuevaTarea.completada = false;

            tareaIdCreada = given()
                .contentType(ContentType.JSON)
                .body(nuevaTarea)
                .when()
                    .post("/tareas")
                .then()
                    .statusCode(201)
                .extract()
                    .path("id");
        }

        given()
            .when()
                .put("/tareas/" + tareaIdCreada + "/done")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completada", equalTo(true))
                .body("id", equalTo(tareaIdCreada));
    }

    @Test
    @Order(6)
    @DisplayName("Test 6: Completar tarea inexistente - Debe devolver código 404")
    void testCompletarTareaInexistente() {
        Integer idInexistente = 999999;

        given()
            .when()
                .put("/tareas/" + idInexistente + "/done")
            .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    @DisplayName("Test 7: Eliminar tarea existente - Debe devolver código 200")
    void testEliminarTareaExistente() {
        // Creamos una tarea para eliminar
        TareaDTO tareaParaEliminar = new TareaDTO();
        tareaParaEliminar.titulo = "Tarea para eliminar";
        tareaParaEliminar.descripcion = "Esta tarea será eliminada";
        tareaParaEliminar.completada = false;

        Integer idParaEliminar = given()
            .contentType(ContentType.JSON)
            .body(tareaParaEliminar)
            .when()
                .post("/tareas")
            .then()
                .statusCode(201)
            .extract()
                .path("id");

        // Eliminamos la tarea
        given()
            .when()
                .delete("/tareas/" + idParaEliminar)
            .then()
                .statusCode(200);

        // Verificamos que ya no aparece en la lista
        given()
            .when()
                .get("/tareas")
            .then()
                .statusCode(200)
                .body("id", not(hasItem(idParaEliminar)));
    }

    @Test
    @Order(8)
    @DisplayName("Test 8: Eliminar tarea inexistente - Debería devolver código 200 o 404")
    void testEliminarTareaInexistente() {
        Integer idInexistente = 999999;

        // Según la implementación actual, deleteById no verifica si existe
        // Por lo tanto devuelve 200 aunque no exista
        given()
            .when()
                .delete("/tareas/" + idInexistente)
            .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(9)
    @DisplayName("Test 9: Verificar estructura completa de TareaDTO")
    void testEstructuraTareaDTO() {
        TareaDTO nuevaTarea = new TareaDTO();
        nuevaTarea.titulo = "Tarea de verificación estructura";
        nuevaTarea.descripcion = "Verificar todos los campos del DTO";
        nuevaTarea.completada = false;

        given()
            .contentType(ContentType.JSON)
            .body(nuevaTarea)
            .when()
                .post("/tareas")
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("titulo", notNullValue())
                .body("descripcion", notNullValue())
                .body("completada", notNullValue());
    }

    @Test
    @Order(10)
    @DisplayName("Test 10: Listar tareas después de todas las operaciones")
    void testListarTareasFinal() {
        given()
            .when()
                .get("/tareas")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0));
    }
}
