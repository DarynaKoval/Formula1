@echo off
chcp 65001 >nul
echo ============================================================
echo   FULL PROGRAM TESTING
echo ============================================================
echo.

cd src

echo [STEP 1] Compiling all files...
echo.

javac -encoding UTF-8 models/*.java models/team/*.java models/team/*Constants.java 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Models compilation failed
    pause
    exit /b 1
)

javac -encoding UTF-8 exceptions/*.java 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Exceptions compilation failed
    pause
    exit /b 1
)

javac -encoding UTF-8 utils/*.java 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Utils compilation failed
    pause
    exit /b 1
)

javac -encoding UTF-8 patterns/creational/*.java patterns/behavioral/*.java patterns/structural/*.java 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Patterns compilation failed
    pause
    exit /b 1
)

if not exist ..\bin mkdir ..\bin

javac -encoding UTF-8 -d ..\bin -cp ..\bin test/*.java 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Tests compilation failed
    pause
    exit /b 1
)

javac -encoding UTF-8 -d ..\bin -cp ..\bin demo/*.java 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Demo compilation failed
    pause
    exit /b 1
)

javac -encoding UTF-8 -d ..\bin -cp ..\bin App.java 2>&1
if %errorlevel% neq 0 (
    echo ERROR: App compilation failed
    pause
    exit /b 1
)

echo.
echo [STEP 2] Running tests...
echo.

cd ..\bin

echo Test 1: TeamMemberTest
java -ea test.TeamMemberTest 2>&1
echo.

echo Test 2: CarTest
java -ea test.CarTest 2>&1
echo.

echo Test 3: ExceptionTest
java -ea test.ExceptionTest 2>&1
echo.

echo Test 4: CreationalPatternsTest
java -ea test.CreationalPatternsTest 2>&1
echo.

echo Test 5: BehavioralPatternsTest
java -ea test.BehavioralPatternsTest 2>&1
echo.

echo Test 6: StructuralPatternsTest
java -ea test.StructuralPatternsTest 2>&1
echo.

echo Test 7: TeamTest
java -ea test.TeamTest 2>&1
echo.

echo Test 8: RaceAndScoringTest
java -ea test.RaceAndScoringTest 2>&1
echo.

echo Test 9: TDDCompleteTest
java -ea test.TDDCompleteTest 2>&1
echo.

echo Test 10: IntegrationTest
java -ea test.IntegrationTest 2>&1
echo.

echo Test 11: SystemTest
java -ea test.SystemTest 2>&1
echo.

cd ..

echo.
echo ============================================================
echo   COMPILATION: OK
echo   TESTS: EXECUTED
echo ============================================================
echo.
echo To run the program:
echo   cd bin
echo   java App
echo.
pause
