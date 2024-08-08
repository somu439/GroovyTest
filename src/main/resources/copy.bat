```batch
@echo off
REM Define the source and destination directories
set "source=\\SharedFolder\Path"
set "destination=C:\LocalFolder\Path"

REM Ensure the destination directory exists
if not exist "%destination%" (
    mkdir "%destination%"
)

REM Copy files from the source to the destination
echo Copying files from %source% to %destination%...
xcopy "%source%\*" "%destination%\" /s /e /y

REM Check if the copy was successful
if %errorlevel% equ 0 (
    echo Files copied successfully.
) else (
    echo An error occurred during the file copy.
)

REM Pause the script to view the output
pause

```