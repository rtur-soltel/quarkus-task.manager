#!/bin/bash

# Script de benchmark para cargar y probar la API de tareas
# Uso: ./load_test.sh [URL_BASE]
# Ejemplo: ./load_test.sh http://localhost:8080

BASE_URL=${1:-http://localhost:8080}
API_URL="${BASE_URL}/tareas"

echo "======================================"
echo "  Benchmark de API de Tareas"
echo "======================================"
echo "URL Base: ${BASE_URL}"
echo ""


# Pausa de 5 segundos antes de iniciar
echo "Esperando 5 segundos antes de iniciar..."
sleep 5
echo "Ejecutando benchmark..."

# Capturar tiempo de inicio del benchmark completo
total_start_time=$(date +%s%3N)

# Array para almacenar los IDs de las tareas creadas
declare -a TASK_IDS

# Función para crear una tarea
create_task() {
    local numero=$1
    local response=$(curl -s -X POST "${API_URL}" \
        -H "Content-Type: application/json" \
        -d "{\"titulo\":\"Tarea ${numero}\",\"descripcion\":\"Descripción de la tarea número ${numero}\",\"completada\":false}")
    
    # Extraer el ID de la respuesta
    local id=$(echo $response | grep -o '"id":[0-9]*' | grep -o '[0-9]*')
    echo $id
}

# Función para marcar una tarea como completada
complete_task() {
    local id=$1
    curl -s -X PUT "${API_URL}/${id}/done" \
        -H "Content-Type: application/json" > /dev/null
}

# Función para consultar una tarea
get_task() {
    local id=$1
    curl -s -X GET "${API_URL}/${id}" \
        -H "Accept: application/json"
}

# Función para listar todas las tareas
list_all_tasks() {
    curl -s -X GET "${API_URL}" \
        -H "Accept: application/json"
}

echo "======================================"
echo "  Fase 1: Creando 200 tareas"
echo "======================================"
start_time=$(date +%s%3N)

for i in $(seq 1 200); do
    task_id=$(create_task $i)
    TASK_IDS+=($task_id)
    
    # Mostrar progreso cada 10 tareas
    if [ $((i % 10)) -eq 0 ]; then
        echo "Creadas $i tareas..."
    fi
done

end_time=$(date +%s%3N)
elapsed=$((end_time - start_time))
echo "✓ 100 tareas creadas en ${elapsed} milisegundos"
echo ""

echo "======================================"
echo "  Fase 2: Completando 50 tareas"
echo "======================================"
start_time=$(date +%s%3N)

for i in $(seq 0 49); do
    complete_task ${TASK_IDS[$i]}
    
    # Mostrar progreso cada 10 tareas
    if [ $(((i + 1) % 10)) -eq 0 ]; then
        echo "Completadas $((i + 1)) tareas..."
    fi
done

end_time=$(date +%s%3N)
elapsed=$((end_time - start_time))
echo "✓ 50 tareas completadas en ${elapsed} milisegundos"
echo ""

echo "======================================"
echo "  Fase 3: Consultando tareas"
echo "======================================"
start_time=$(date +%s%3N)

# Consultar las primeras 10 tareas individualmente
echo "Consultando las primeras 10 tareas individualmente..."
for i in $(seq 0 9); do
    get_task ${TASK_IDS[$i]} > /dev/null
done
echo "✓ Consultadas 10 tareas individuales"

# Listar todas las tareas
echo "Consultando todas las tareas..."
response=$(list_all_tasks)
task_count=$(echo $response | grep -o '"id"' | wc -l)

end_time=$(date +%s%3N)
elapsed=$((end_time - start_time))
echo "✓ Listado completo obtenido: ${task_count} tareas en ${elapsed} milisegundos"
echo ""

echo "======================================"
echo "  Fase 4: Eliminando 50 tareas"
echo "======================================"
start_time=$(date +%s%3N)

# Eliminar las tareas de la segunda mitad (50-99)
for i in $(seq 50 99); do
    curl -s -X DELETE "${API_URL}/${TASK_IDS[$i]}" > /dev/null
    
    # Mostrar progreso cada 10 tareas
    if [ $(((i - 49) % 10)) -eq 0 ]; then
        echo "Eliminadas $((i - 49)) tareas..."
    fi
done

end_time=$(date +%s%3N)
elapsed=$((end_time - start_time))
echo "✓ 50 tareas eliminadas en ${elapsed} milisegundos"
echo ""

echo "======================================"
echo "  Fase 5: Consultando 25 tareas por ID"
echo "======================================"
start_time=$(date +%s%3N)

# Consultar las primeras 25 tareas por ID
echo "Consultando 25 tareas individuales por ID..."
for i in $(seq 0 24); do
    get_task ${TASK_IDS[$i]} > /dev/null
done

end_time=$(date +%s%3N)
elapsed=$((end_time - start_time))
echo "✓ 25 tareas consultadas por ID en ${elapsed} milisegundos"
echo ""

echo "======================================"
echo "  Resumen de tareas"
echo "======================================"
# Obtener estadísticas finales de las tareas
response=$(list_all_tasks)
task_count=$(echo $response | grep -o '"id"' | wc -l)
completed_count=$(echo $response | grep -o '"completada":true' | wc -l)
pending_count=$(echo $response | grep -o '"completada":false' | wc -l)

echo "Total de tareas restantes: ${task_count}"
echo "Tareas completadas: ${completed_count}"
echo "Tareas pendientes: ${pending_count}"
echo ""

# Calcular tiempo total transcurrido
total_end_time=$(date +%s%3N)
total_elapsed=$((total_end_time - total_start_time))
echo "Tiempo total del benchmark: ${total_elapsed} milisegundos"
echo ""

echo "======================================"
echo "  Benchmark completado"
echo "======================================"
