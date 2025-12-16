@echo off
REM Script para compilación estándar (JVM) del proyecto multimodulo
echo ====================================
echo Compilación estándar JVM
echo ====================================
echo.

cd /d "%~dp0"

echo Limpiando proyecto...
call mvnw.cmd clean -f pom-parent.xml

echo.
echo Compilando proyecto con Maven...
call mvnw.cmd package -f pom-parent.xml -DskipTests

echo.
echo ====================================
echo Compilación completada
echo ====================================
echo El artefacto ejecutable está en:
echo task-manager-infrastructure\target\quarkus-app\
echo.
echo Para ejecutar:
echo java -jar task-manager-infrastructure\target\quarkus-app\quarkus-run.jar
echo.
