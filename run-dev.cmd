@echo off
REM Script para ejecutar la aplicación en modo desarrollo
echo ====================================
echo Ejecutando en modo desarrollo
echo ====================================
echo.

cd /d "%~dp0"

echo Iniciando Quarkus en modo dev...
call mvnw.cmd quarkus:dev -f task-manager-infrastructure\pom.xml

