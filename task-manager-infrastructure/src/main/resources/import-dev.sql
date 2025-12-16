-- This file allow to write SQL commands that will be emitted in test and dev.
-- Tareas iniciales para la aplicación de gestión de tareas
INSERT INTO Tarea (id, titulo, descripcion, completada) VALUES (1, 'Aprender Quarkus', 'Estudiar el framework Quarkus y sus características principales', false);
INSERT INTO Tarea (id, titulo, descripcion, completada) VALUES (2, 'Crear API REST', 'Implementar endpoints REST para gestión de tareas', true);
INSERT INTO Tarea (id, titulo, descripcion, completada) VALUES (3, 'Añadir validaciones', 'Implementar validaciones con Hibernate Validator', true);
INSERT INTO Tarea (id, titulo, descripcion, completada) VALUES (4, 'Escribir tests unitarios', 'Crear tests con JUnit 5 y RestAssured', false);
INSERT INTO Tarea (id, titulo, descripcion, completada) VALUES (5, 'Documentar API', 'Generar documentación OpenAPI con Swagger', false);
ALTER SEQUENCE tarea_seq RESTART WITH 6;
