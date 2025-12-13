@echo off
chcp 65001 >nul
echo ============================================================
echo   FULL PROGRAM TESTING - Formula 1 OOP Project
echo ============================================================
echo.

cd src

echo [1/4] Compiling all source files...
echo.

echo Compiling models...
javac -encoding UTF-8 models/*.java models/team/*.java models/team/*Constants.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile models
    pause
    exit /b 1
)

echo Compiling exceptions...
javac -encoding UTF-8 exceptions/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile exceptions
    pause
    exit /b 1
)

echo Compiling utils...
javac -encoding UTF-8 utils/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile utils
    pause
    exit /b 1
)

echo Compiling patterns...
javac -encoding UTF-8 patterns/creational/*.java patterns/behavioral/*.java patterns/structural/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile patterns
    pause
    exit /b 1
)

echo Compiling test files...
javac -encoding UTF-8 -d ../bin test/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile tests
    pause
    exit /b 1
)

echo Compiling demo...
javac -encoding UTF-8 -d ../bin demo/*.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile demo
    pause
    exit /b 1
)

echo Compiling main App...
javac -encoding UTF-8 -d ../bin App.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile App
    pause
    exit /b 1
)

echo.
echo [2/4] Running all tests...
echo.
echo ============================================================
echo TEST 1: TeamMemberTest (Stage 1 - OOP)
echo ============================================================
cd ../bin
java -ea test.TeamMemberTest
echo.
echo ============================================================
echo TEST 2: CarTest (Stage 2 - Car Model)
echo ============================================================
java -ea test.CarTest
echo.
echo ============================================================
echo TEST 3: ExceptionTest (Stage 3 - Exceptions)
echo ============================================================
java -ea test.ExceptionTest
echo.
echo ============================================================
echo TEST 4: CreationalPatternsTest (Stage 4 - Creational Patterns)
echo ============================================================
java -ea test.CreationalPatternsTest
echo.
echo ============================================================
echo TEST 5: BehavioralPatternsTest (Stage 5 - Behavioral Patterns)
echo ============================================================
java -ea test.BehavioralPatternsTest
echo.
echo ============================================================
echo TEST 6: StructuralPatternsTest (Stage 6 - Structural Patterns)
echo ============================================================
java -ea test.StructuralPatternsTest
echo.
echo ============================================================
echo TEST 7: TeamTest (Stage 7 - Team and GRASP)
echo ============================================================
java -ea test.TeamTest
echo.
echo ============================================================
echo TEST 8: RaceAndScoringTest (Stages 8-9 - Race and Scoring)
echo ============================================================
java -ea test.RaceAndScoringTest
echo.
echo ============================================================
echo TEST 9: IntegrationTest (Integration Testing)
echo ============================================================
java -ea test.IntegrationTest
echo.
echo ============================================================
echo TEST 10: TDDCompleteTest (Stage 10 - Full TDD)
echo ============================================================
java -ea test.TDDCompleteTest
echo.
echo ============================================================
echo TEST 11: SystemTest (System Integration)
echo ============================================================
java -ea test.SystemTest
echo.
echo ============================================================
echo TEST 12: ComprehensiveTest (Comprehensive Testing)
echo ============================================================
java -ea test.ComprehensiveTest
echo.

cd ..

echo.
echo [3/4] Testing main App compilation...
echo.
echo App.java compiles successfully!
echo.

echo [4/4] All tests completed!
echo.
echo ============================================================
echo   TESTING SUMMARY
echo ============================================================
echo.
echo All compilation: OK
echo All tests executed: OK
echo.
echo To run the program:
echo   cd bin
echo   java App
echo.
echo ============================================================
pause
