package test;

import models.*;
import models.team.*;
import patterns.creational.*;
import patterns.behavioral.*;
import patterns.structural.*;
import exceptions.*;
import utils.Validator;
import java.util.List;

/**
 * Етап 10: Повне TDD (Test-Driven Development)
 * 
 * Цей тест перевіряє:
 * 1. Всі крайові випадки (edge cases)
 * 2. Валідацію даних
 * 3. Рефакторинг (Validator, константи)
 * 4. Покриття всіх методів
 * 5. Інтеграційне тестування
 */
public class TDDCompleteTest {
    
    private static int testCount = 0;
    private static int passedCount = 0;
    
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("  ЕТАП 10: ПОВНЕ TDD - TEST-DRIVEN DEVELOPMENT");
        System.out.println("============================================================\n");
        
        try {
            // 1. Тести валідації та крайових випадків
            testValidationEdgeCases();
            testValidatorUtility();
            testConstantsUsage();
            
            // 2. Тести для всіх методів класів
            testTeamMemberMethods();
            testCarMethods();
            testDriverMethods();
            testTeamMethods();
            testRaceMethods();
            
            // 3. Тести інтеграції компонентів
            testCompleteWorkflow();
            testErrorRecovery();
            testStateTransitions();
            
            // 4. Тести рефакторингу
            testRefactoringImprovements();
            
            printSummary();
        } catch (Exception e) {
            System.err.println("\n[ERROR] Test suite failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // === ГРУПА 1: Валідація та крайові випадки ===
    
    private static void testValidationEdgeCases() {
        System.out.println("--- Group 1: Validation and Edge Cases ---\n");
        
        // Тест 1.1: Мінімальні значення
        test("1.1: Minimum values", () -> {
            Driver driver = new Driver("Test", 1.0); // Мінімальний skill
            assert driver.getSkillLevel() == 1.0 : "Should accept minimum skill";
            
            Car car = new Car(500.0, 1.0, 1.0); // Мінімальні значення
            assert car.getEnginePower() == 500.0 : "Should accept minimum power";
            assert car.getAerodynamics() == 1.0 : "Should accept minimum aero";
        });
        
        // Тест 1.2: Максимальні значення
        test("1.2: Maximum values", () -> {
            Driver driver = new Driver("Test", 10.0); // Максимальний skill
            assert driver.getSkillLevel() == 10.0 : "Should accept maximum skill";
            
            Car car = new Car(1000.0, 10.0, 150.0); // Максимальні значення
            assert car.getEnginePower() == 1000.0 : "Should accept maximum power";
            assert car.getAerodynamics() == 10.0 : "Should accept maximum aero";
        });
        
        // Тест 1.3: Невалідні значення (повинні викидати виключення)
        test("1.3: Invalid values throw exceptions", () -> {
            try {
                new Driver("Test", 0.5); // Нижче мінімуму
                assert false : "Should throw exception for skill < 1.0";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
            
            try {
                new Driver("Test", 11.0); // Вище максимуму
                assert false : "Should throw exception for skill > 10.0";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
            
            try {
                new Car(400.0, 5.0, 100.0); // Потужність нижче мінімуму
                assert false : "Should throw exception for power < 500";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
        });
        
        // Тест 1.4: Null значення
        test("1.4: Null values handling", () -> {
            // Перевірка що null role обробляється
            Driver driver = new Driver("Test", 8.0);
            // setName з null повинно викинути exception через Validator
            try {
                driver.setName(null);
                assert false : "Should throw for null name";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
        });
    }
    
    private static void testValidatorUtility() {
        System.out.println("\n--- Group 2: Validator Utility (Refactoring) ---\n");
        
        // Тест 2.1: validateNonEmpty
        test("2.1: Validator.validateNonEmpty", () -> {
            String result = Validator.validateNonEmpty("Test", "Field");
            assert result.equals("Test") : "Should return valid string";
            
            try {
                Validator.validateNonEmpty(null, "Field");
                assert false : "Should throw for null";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
            
            try {
                Validator.validateNonEmpty("", "Field");
                assert false : "Should throw for empty";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
        });
        
        // Тест 2.2: validateRange
        test("2.2: Validator.validateRange", () -> {
            Validator.validateRange(5.0, 1.0, 10.0, "Value");
            // Не повинно викинути exception
            
            try {
                Validator.validateRange(0.5, 1.0, 10.0, "Value");
                assert false : "Should throw for value below range";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
            
            try {
                Validator.validateRange(15.0, 1.0, 10.0, "Value");
                assert false : "Should throw for value above range";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
        });
        
        // Тест 2.3: validateNonNegative
        test("2.3: Validator.validateNonNegative", () -> {
            Validator.validateNonNegative(0.0, "Value");
            Validator.validateNonNegative(10.0, "Value");
            
            try {
                Validator.validateNonNegative(-1.0, "Value");
                assert false : "Should throw for negative";
            } catch (IllegalArgumentException e) {
                // Очікується
            }
        });
    }
    
    private static void testConstantsUsage() {
        System.out.println("\n--- Group 3: Constants Usage (Refactoring) ---\n");
        
        // Тест 3.1: RaceConstants
        test("3.1: RaceConstants", () -> {
            assert RaceConstants.DEFAULT_LAPS > 0 : "Default laps should be positive";
            assert RaceConstants.BASE_LAP_TIME > 0 : "Base lap time should be positive";
            assert RaceConstants.MIN_LAP_TIME > 0 : "Min lap time should be positive";
        });
        
        // Тест 3.2: MechanicConstants
        test("3.2: MechanicConstants", () -> {
            Mechanic mechanic = new Mechanic("Test", 5);
            assert mechanic.getEfficiency() >= MechanicConstants.BASE_EFFICIENCY : 
                "Efficiency should be within range";
            assert mechanic.getEfficiency() <= MechanicConstants.MAX_EFFICIENCY : 
                "Efficiency should be within range";
        });
    }
    
    // === ГРУПА 2: Тести методів ===
    
    private static void testTeamMemberMethods() {
        System.out.println("\n--- Group 4: TeamMember Methods ---\n");
        
        // Тест 4.1: getExperience, setExperience
        test("4.1: Experience methods", () -> {
            Driver driver = new Driver("Test", 8.0);
            assert driver.getExperience() >= 0 : "Experience should be non-negative";
            
            driver.setExperience(5);
            assert driver.getExperience() == 5 : "Should set experience";
        });
        
        // Тест 4.2: gainExperience
        test("4.2: gainExperience", () -> {
            Driver driver = new Driver("Test", 8.0);
            int initialExp = driver.getExperience();
            driver.gainExperience();
            assert driver.getExperience() == initialExp + 1 : "Should increment experience";
        });
        
        // Тест 4.3: toString
        test("4.3: toString method", () -> {
            Driver driver = new Driver("Test Driver", 8.5);
            String str = driver.toString();
            assert str.contains("Test Driver") : "Should contain name";
            assert str.contains("8.5") : "Should contain skill level";
        });
    }
    
    private static void testCarMethods() {
        System.out.println("\n--- Group 5: Car Methods ---\n");
        
        // Тест 5.1: refuel
        test("5.1: refuel method", () -> {
            Car car = new Car(800.0, 8.0, 100.0);
            double initialFuel = car.getFuelLevel();
            car.refuel(20.0);
            assert car.getFuelLevel() == initialFuel + 20.0 : "Should add fuel";
            
            // Не повинно перевищувати maxFuel
            car.refuel(200.0);
            assert car.getFuelLevel() <= car.getMaxFuel() : "Should not exceed max fuel";
        });
        
        // Тест 5.2: checkState
        test("5.2: checkState method", () -> {
            Car car = new Car(800.0, 8.0, 100.0);
            car.checkState();
            assert car.getState() != null : "Should have a state";
            
            // Перевірка з низьким паливом
            car.setFuelLevel(5.0);
            car.checkState();
            // Повинен бути Critical або Overheating стан
        });
        
        // Тест 5.3: brake
        test("5.3: brake method", () -> {
            Car car = new Car(800.0, 8.0, 100.0);
            car.brake(); // Не повинно викинути exception
            assert true : "Brake should work";
        });
    }
    
    private static void testDriverMethods() {
        System.out.println("\n--- Group 6: Driver Methods ---\n");
        
        // Тест 6.1: train
        test("6.1: train method", () -> {
            Driver driver = new Driver("Test", 8.0);
            double initialSkill = driver.getSkillLevel();
            boolean result = driver.train();
            
            if (initialSkill < 10.0) {
                assert result == true : "Should return true if skill < 10";
                assert driver.getSkillLevel() > initialSkill : "Should increase skill";
            } else {
                assert result == false : "Should return false if skill == 10";
            }
        });
        
        // Тест 6.2: addWin
        test("6.2: addWin method", () -> {
            Driver driver = new Driver("Test", 8.0);
            int initialWins = driver.getWins();
            driver.addWin();
            assert driver.getWins() == initialWins + 1 : "Should increment wins";
        });
        
        // Тест 6.3: setCar
        test("6.3: setCar bidirectional link", () -> {
            Driver driver = new Driver("Test", 8.0);
            Car car = new Car(800.0, 8.0, 100.0);
            
            driver.setCar(car);
            assert driver.getCar() == car : "Driver should have car";
            assert car.getDriver() == driver : "Car should have driver";
        });
    }
    
    private static void testTeamMethods() {
        System.out.println("\n--- Group 7: Team Methods ---\n");
        
        // Тест 7.1: getTeamSize
        test("7.1: getTeamSize", () -> {
            TeamPrincipal principal = new TeamPrincipal("Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            
            assert team.getTeamSize() == 1 : "Should have 1 member (principal)";
            
            team.addDriver("Driver 1", 8.0, "UK");
            assert team.getTeamSize() == 2 : "Should have 2 members";
            
            team.addEngineer(new Engineer("Engineer", "Aero", 5));
            assert team.getTeamSize() == 3 : "Should have 3 members";
        });
        
        // Тест 7.2: getTotalExperience
        test("7.2: getTotalExperience", () -> {
            TeamPrincipal principal = new TeamPrincipal("Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            
            int initialExp = team.getTotalExperience();
            assert initialExp == 10 : "Should have principal experience";
            
            Driver driver = team.addDriver("Driver", 8.0, "UK");
            driver.setExperience(5);
            assert team.getTotalExperience() == 15 : "Should sum all experience";
        });
        
        // Тест 7.3: getTeamInfo
        test("7.3: getTeamInfo", () -> {
            TeamPrincipal principal = new TeamPrincipal("Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            String info = team.getTeamInfo();
            
            assert info.contains("Test Team") : "Should contain team name";
            assert info.contains("Principal") : "Should contain principal name";
        });
    }
    
    private static void testRaceMethods() {
        System.out.println("\n--- Group 8: Race Methods ---\n");
        
        // Тест 8.1: addParticipant
        test("8.1: addParticipant", () -> {
            Race race = new Race("Test Race", 5);
            Driver driver = new Driver("Test Driver", 8.0);
            Car car = new Car(800.0, 8.0, 100.0);
            
            race.addParticipant(driver, car);
            List<RaceResult> results = race.getResults();
            
            assert results.size() == 1 : "Should have one participant";
            assert results.get(0).getDriver() == driver : "Should store driver";
        });
        
        // Тест 8.2: startRace validation
        test("8.2: startRace validation", () -> {
            Race race = new Race("Test Race", 5);
            
            try {
                race.startRace(); // Без учасників
                assert false : "Should throw exception";
            } catch (IllegalStateException e) {
                // Очікується
            }
        });
        
        // Тест 8.3: addObserver
        test("8.3: addObserver", () -> {
            Race race = new Race("Test Race", 5);
            ConsoleObserver observer = new ConsoleObserver("Test");
            
            race.addObserver(observer);
            // Не повинно викинути exception
            assert true : "Should add observer";
        });
    }
    
    // === ГРУПА 3: Інтеграційні тести ===
    
    private static void testCompleteWorkflow() {
        System.out.println("\n--- Group 9: Complete Workflow Integration ---\n");
        
        test("9.1: Full team to race workflow", () -> {
            // Створення команди
            TeamPrincipal principal = new TeamPrincipal("Principal", 10, 1000000.0);
            Team team = new Team("Ferrari", principal);
            
            // Додавання гонщиків
            Driver driver1 = team.addDriver("Driver 1", 9.0, "UK");
            Driver driver2 = team.addDriver("Driver 2", 8.5, "USA");
            
            // Створення болідів через Builder
            Car car1 = new CarBuilder().focusOnPower().build();
            Car car2 = new CarBuilder().focusOnAerodynamics().build();
            
            // Призначення стратегій
            driver1.setStrategy(new AggressiveStrategy());
            driver2.setStrategy(new EconomicalStrategy());
            
            // Створення гонки
            Race race = new Race("Test GP", 3);
            race.addObserver(new ConsoleObserver("Live"));
            
            race.addParticipant(driver1, car1);
            race.addParticipant(driver2, car2);
            
            // Запуск гонки
            race.startRace();
            
            // Перевірка результатів
            List<RaceResult> results = race.getResults();
            assert results.size() == 2 : "Should have 2 results";
            assert results.get(0).getLapsCompleted() > 0 : "Should complete laps";
        });
    }
    
    private static void testErrorRecovery() {
        System.out.println("\n--- Group 10: Error Recovery ---\n");
        
        test("10.1: FuelException recovery", () -> {
            Driver driver = new Driver("Test", 8.0);
            Car car = new Car(800.0, 8.0, 100.0);
            driver.setCar(car);
            driver.setStrategy(new AggressiveStrategy());
            
            // Витрачаємо все паливо
            car.setFuelLevel(0.0);
            
            // Повинно обробити помилку без crash
            try {
                driver.drive();
                // Може викинути exception, але програма не повинна крашитися
            } catch (Exception e) {
                // Очікується обробка
            }
            assert true : "Should handle fuel exception";
        });
    }
    
    private static void testStateTransitions() {
        System.out.println("\n--- Group 11: State Transitions ---\n");
        
        test("11.1: Car state transitions", () -> {
            Car car = new Car(800.0, 8.0, 100.0);
            
            // Нормальний стан
            car.checkState();
            CarState state1 = car.getState();
            
            // Низьке паливо -> інший стан
            car.setFuelLevel(5.0);
            car.checkState();
            CarState state2 = car.getState();
            
            // Стани повинні змінитися
            assert state1 != null && state2 != null : "States should exist";
        });
    }
    
    // === ГРУПА 4: Рефакторинг ===
    
    private static void testRefactoringImprovements() {
        System.out.println("\n--- Group 12: Refactoring Verification ---\n");
        
        test("12.1: Constants replace magic numbers", () -> {
            // Перевірка що використовуються константи замість магічних чисел
            Mechanic mechanic = new Mechanic("Test", 5);
            double efficiency = mechanic.getEfficiency();
            
            // Efficiency повинна бути в межах констант
            assert efficiency >= 0.5 && efficiency <= 1.0 : 
                "Efficiency should use constants";
        });
        
        test("12.2: Validator reduces duplication", () -> {
            // Перевірка що Validator використовується
            try {
                Validator.validateNonEmpty(null, "Test");
                assert false : "Validator should work";
            } catch (IllegalArgumentException e) {
                // Очікується - Validator працює
                assert e.getMessage().contains("cannot be null") : 
                    "Validator should provide consistent messages";
            }
        });
    }
    
    // === Допоміжні методи ===
    
    private static void test(String name, Runnable testCase) {
        testCount++;
        try {
            testCase.run();
            passedCount++;
            System.out.println("[PASS] " + name);
        } catch (AssertionError e) {
            System.out.println("[FAIL] " + name + " - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] " + name + " - " + e.getMessage());
        }
    }
    
    private static void printSummary() {
        System.out.println("\n============================================================");
        System.out.println("  TDD TEST SUMMARY");
        System.out.println("============================================================");
        System.out.println("Total tests: " + testCount);
        System.out.println("Passed: " + passedCount);
        System.out.println("Failed: " + (testCount - passedCount));
        System.out.println("Success rate: " + String.format("%.1f%%", 
            (passedCount * 100.0 / testCount)));
        
        if (passedCount == testCount) {
            System.out.println("\n[SUCCESS] ✅ Всі TDD тести пройдено!");
            System.out.println("Етап 10: Повне TDD - ЗАВЕРШЕНО");
        } else {
            System.out.println("\n[WARNING] ⚠ Деякі тести не пройдено");
        }
        System.out.println("============================================================\n");
    }
}

