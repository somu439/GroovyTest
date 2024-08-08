```batch
@echo off
REM Define the directory to search for the .config file
set "configDir=C:\Path\To\Config\Directory"

REM Change to the directory where the .config files are located
cd /d "%configDir%"

REM Find the first .config file in the directory
for %%f in (*.config) do (
    set "configFile=%%f"
    goto :found
)

:found
REM Check if a .config file was found
if not defined configFile (
    echo No .config file found in %configDir%.
    exit /b 1
)

REM Run the Java command with the found .config file
echo Running Java command with %configFile%...
java -jar YourJavaApplication.jar "%configFile%"

REM Pause the script to view the output
pause

```