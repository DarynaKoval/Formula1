@echo off
chcp 65001 >nul
echo Compiling Formula 1 Project...
echo.

cd src

echo Compiling models...
javac -encoding UTF-8 -d ../bin models/*.java models/team/*.java models/team/*Constants.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile models
    pause
    exit /b 1
)

echo Compiling exceptions...
javac -encoding UTF-8 -d ../bin exceptions/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile exceptions
    pause
    exit /b 1
)

echo Compiling utils...
javac -encoding UTF-8 -d ../bin utils/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile utils
    pause
    exit /b 1
)

echo Compiling patterns...
javac -encoding UTF-8 -d ../bin patterns/creational/*.java patterns/behavioral/*.java patterns/structural/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile patterns
    pause
    exit /b 1
)

echo Compiling demo...
javac -encoding UTF-8 -d ../bin -cp ../bin demo/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile demo
    pause
    exit /b 1
)

echo Compiling test...
javac -encoding UTF-8 -d ../bin -cp ../bin test/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile test
    pause
    exit /b 1
)

echo Compiling main App...
javac -encoding UTF-8 -d ../bin -cp ../bin App.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile App
    pause
    exit /b 1
)

cd ..

echo.
echo ========================================
echo Compilation completed successfully!
echo ========================================
echo.
echo To run the program:
echo   cd bin
echo   java App
echo.
pause
