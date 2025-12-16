@echo off
REM Script para compilación nativa del proyecto multimodulo
echo ====================================
echo Compilación nativa con GraalVM
echo ====================================
echo.
echo REQUISITOS:
echo - GraalVM instalado
echo - Variable GRAALVM_HOME configurada
echo - Visual Studio Build Tools instalado
echo.

cd /d "%~dp0"

echo Limpiando proyecto...
call mvnw.cmd clean -f pom-parent.xml

echo.
echo Compilando proyecto nativo (esto puede tardar varios minutos)...
call mvnw.cmd package -f pom-parent.xml -Pnative -DskipTests

echo.
echo ====================================
echo Compilación nativa completada
echo ====================================
echo El ejecutable nativo está en:
echo task-manager-infrastructure\target\task-manager-infrastructure-1.0.0-SNAPSHOT-runner.exe
echo.
echo Para ejecutar:
echo .\task-manager-infrastructure\target\task-manager-infrastructure-1.0.0-SNAPSHOT-runner.exe
echo.
