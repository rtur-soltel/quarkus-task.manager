# Task Manager - Proyecto Multimodulo con Clean Architecture

Proyecto Quarkus refactorizado con arquitectura limpia (Clean Architecture) usando múltiples módulos Maven.

## 📁 Estructura del Proyecto

```
task-manager-parent/
├── task-manager-domain/          # Capa de Dominio
│   └── src/main/java/.../domain/
│       ├── model/                 # Entidades de dominio
│       └── repository/            # Contratos (puertos)
│
├── task-manager-application/     # Capa de Aplicación
│   └── src/main/java/.../application/
│       ├── service/               # Casos de uso
│       ├── dto/                   # DTOs
│       └── mapper/                # Mappers
│
└── task-manager-infrastructure/  # Capa de Infraestructura
    ├── src/main/java/.../infrastructure/
    │   ├── controller/            # Controladores REST
    │   └── persistence/           # Implementaciones de repositorio
    ├── src/main/resources/
    │   ├── application.properties
    │   └── import-dev.sql
    └── src/test/                  # Tests de integración
```

## 🏗️ Arquitectura Clean Architecture

### Domain (Núcleo del negocio)
- **Entidades**: Modelos de dominio puros sin dependencias de frameworks
- **Puertos**: Interfaces que definen contratos (ej: TareaRepository)
- Sin dependencias externas, solo lógica de negocio

### Application (Casos de uso)
- **Servicios**: Orquestación de casos de uso
- **DTOs**: Objetos de transferencia de datos
- **Mappers**: Conversión entre entidades y DTOs
- Depende solo del dominio

### Infrastructure (Detalles técnicos)
- **Controladores REST**: Adaptadores de entrada
- **Persistencia**: Adaptadores de salida (implementación de repositorios)
- **Configuración**: Properties, recursos
- Depende de Application y Domain

## 🚀 Scripts de Compilación y Ejecución

### Compilación Estándar (JVM)
```cmd
build-jvm.cmd
```
Compila el proyecto para ejecutarse en la JVM tradicional.

### Compilación Nativa
```cmd
build-native.cmd
```
Compila el proyecto como ejecutable nativo con GraalVM (requiere GraalVM instalado).

### Ejecución en Modo Desarrollo
```cmd
run-dev.cmd
```
Ejecuta en modo desarrollo con hot-reload.

### Ejecución JVM
```cmd
run-jvm.cmd
```
Ejecuta la aplicación compilada en modo JVM.

### Ejecución Nativa
```cmd
run-native.cmd
```
Ejecuta el ejecutable nativo.

### Ejecutar Tests
```cmd
run-tests.cmd
```
Ejecuta todos los tests del proyecto.

## 📊 Pruebas de Estrés

El script de benchmark está disponible en `benchmark/load_test.sh`:

```bash
# Para modo JVM
./benchmark/load_test.sh http://localhost:8080

# Para modo nativo
./benchmark/load_test.sh http://localhost:8080
```

## 🔧 Requisitos

- **JDK 21** o superior
- **Maven 3.8+** (incluido como wrapper)
- Para compilación nativa:
  - **GraalVM 21**
  - **Visual Studio Build Tools** (en Windows)
  - Variable `GRAALVM_HOME` configurada

## 📝 Endpoints API

- `GET /tareas` - Listar todas las tareas
- `GET /tareas/{id}` - Obtener una tarea por ID
- `POST /tareas` - Crear una nueva tarea
- `PUT /tareas/{id}/done` - Marcar tarea como completada
- `DELETE /tareas/{id}` - Eliminar una tarea

Documentación OpenAPI disponible en: http://localhost:8080/q/swagger-ui

## 📈 Métricas de Rendimiento

### Procedimiento para recopilar métricas:

1. **Compilar ambas versiones**:
   ```cmd
   build-jvm.cmd
   build-native.cmd
   ```

2. **Ejecutar en modo JVM**:
   ```cmd
   run-jvm.cmd
   ```
   - Anotar tiempo de inicio
   - Ejecutar benchmark: `./benchmark/load_test.sh http://localhost:8080`
   - Anotar métricas de rendimiento y memoria

3. **Ejecutar en modo nativo**:
   ```cmd
   run-native.cmd
   ```
   - Anotar tiempo de inicio
   - Ejecutar benchmark: `./benchmark/load_test.sh http://localhost:8080`
   - Anotar métricas de rendimiento y memoria

### Métricas a recopilar:
- ⏱️ **Tiempo de inicio** de la aplicación
- 💾 **Uso de memoria** (RSS memory)
- 📊 **Throughput** (requests/segundo)
- ⚡ **Latencia** (P50, P95, P99)
- 📦 **Tamaño del artefacto** (JAR vs ejecutable nativo)

## 🛠️ Comandos Maven Útiles

```bash
# Compilar todo el proyecto
mvnw clean package -f pom-parent.xml

# Compilar solo un módulo
mvnw clean package -f task-manager-domain/pom.xml

# Ejecutar tests
mvnw test -f pom-parent.xml

# Compilar en modo nativo
mvnw package -Pnative -f pom-parent.xml

# Modo desarrollo
mvnw quarkus:dev -f task-manager-infrastructure/pom.xml
```

## 📚 Tecnologías

- **Quarkus 3.30.3** - Framework Java nativo de Kubernetes
- **Hibernate ORM + Panache** - Persistencia
- **H2 Database** - Base de datos en memoria
- **RESTEasy** - API REST
- **SmallRye OpenAPI** - Documentación API
- **JUnit 5 + RestAssured** - Testing
- **GraalVM** - Compilación nativa

## 🎯 Ventajas de la Arquitectura Actual

1. **Separación de responsabilidades**: Cada capa tiene un propósito claro
2. **Testabilidad**: Las capas pueden testearse de forma independiente
3. **Mantenibilidad**: Cambios en infraestructura no afectan al dominio
4. **Reutilización**: El dominio puede usarse con diferentes infraestructuras
5. **Inversión de dependencias**: La infraestructura depende del dominio, no al revés

## 📄 Licencia

Este proyecto es parte de la formación de Quarkus Native en AGAPA - Junta de Andalucía.
