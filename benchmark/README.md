# Benchmark y Testing con PostgreSQL

Este directorio contiene los recursos necesarios para realizar pruebas de carga y desplegar la aplicación con PostgreSQL.

## Archivos creados

- **docker-compose-postgres.yml**: Servicio PostgreSQL para testing
- **docker-compose-app.yml**: Servicio de la aplicación Quarkus
- **.env**: Variables de entorno para PostgreSQL
- **load_test.sh**: Script de pruebas de carga

## Uso

### 1. Iniciar PostgreSQL

```bash
cd benchmark
docker-compose -f docker-compose-postgres.yml --env-file .env up -d
```

Verifica que PostgreSQL esté funcionando:
```bash
docker-compose -f docker-compose-postgres.yml ps
docker-compose -f docker-compose-postgres.yml logs postgres
```

### 2. Compilar la aplicación con el perfil PostgreSQL

Desde la raíz del proyecto:

```bash
# Para JVM
./mvnw clean package -Ppostgresql -Dquarkus.profile=prod

# Para nativo
./mvnw clean package -Ppostgresql,native -Dquarkus.profile=prod
```

### 3. Construir la imagen Docker

```bash
# Para JVM
docker build -f src/main/docker/Dockerfile.jvm -t task-manager:latest .

# Para nativo
docker build -f src/main/docker/Dockerfile.native -t task-manager:latest .
```

### 4. Iniciar la aplicación

```bash
cd benchmark
docker-compose -f docker-compose-app.yml --env-file .env up -d
```

### 5. Verificar el despliegue

```bash
# Health check
curl http://localhost:8080/q/health

# API
curl http://localhost:8080/api/tareas
```

### 6. Detener los servicios

```bash
# Detener aplicación
docker-compose -f docker-compose-app.yml down

# Detener PostgreSQL (conserva los datos)
docker-compose -f docker-compose-postgres.yml down

# Detener PostgreSQL y eliminar datos
docker-compose -f docker-compose-postgres.yml down -v
```

## Configuración

### Variables de entorno (.env)

```properties
POSTGRES_DB=taskmanager
POSTGRES_USER=quarkus
POSTGRES_PASSWORD=quarkus123
POSTGRES_PORT=5432
```

### Personalización del docker-compose-app.yml

Puedes modificar:
- Puertos expuestos
- Variables de entorno adicionales
- Límites de recursos (memoria, CPU)
- Configuración de health checks

Ejemplo con recursos limitados:

```yaml
services:
  task-manager-app:
    # ... configuración existente ...
    deploy:
      resources:
        limits:
          cpus: '1'
          memory: 512M
        reservations:
          cpus: '0.5'
          memory: 256M
```

## Conexión directa a PostgreSQL

```bash
docker exec -it task-manager-postgres psql -U quarkus -d taskmanager
```

## Pruebas de carga

Una vez la aplicación esté en ejecución:

```bash
cd benchmark
./load_test.sh
```
