@echo off
title My Java Application
echo Starting application...

set "CURRENT_DIR=%~dp0"

for %%f in ("%CURRENT_DIR%*.jar") do set JAR_FILE=%%f

java -jar "%JAR_FILE%"

pause