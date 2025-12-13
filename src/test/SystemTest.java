package test;

import models.*;
import models.team.*;
import patterns.creational.*;
import patterns.behavioral.*;
import patterns.structural.*;
import exceptions.*;
import java.util.List;

/**
 * System Test - перевіряє роботу всієї програми разом
 * Це інтеграційний тест який демонструє що всі компоненти працюють разом
 */
public class SystemTest {
    
    private static int testCount = 0;
    private static int passedCount = 0;
    
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("     SYSTEM TEST - Full Program Integration Test           ");
        System.out.println("============================================================\n");
        
        try {
            testTeamCreation();
            testCarBuilding();
            testPatterns();
            testRaceSimulation();
            testErrorHandling();
            
            System.out.println("\n============================================================");
            System.out.println("TEST SUMMARY");
            System.out.println("============================================================");
            System.out.println("Total tests: " + testCount);
            System.out.println("Passed: " + passedCount);
            System.out.println("Failed: " + (testCount - passedCount));
            
            if (passedCount == testCount) {
                System.out.println("\n[SUCCESS] All system tests passed!");
            } else {
                System.out.println("\n[FAILURE] Some tests failed!");
            }
        } catch (Exception e) {
            System.err.println("\n[ERROR] System test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Тест 1: Створення команди
    private static void testTeamCreation() {
        System.out.println("--- Test 1: Team Creation ---");
        
        try {
            TeamPrincipal principal = new TeamPrincipal("Test Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            
            Driver driver1 = team.addDriver("Driver 1", 9.0, "UK");
            Driver driver2 = team.addDriver("Driver 2", 8.5, "USA");
            
            Engineer engineer = new Engineer("Engineer 1", "Aerodynamics", 5);
            Mechanic mechanic = new Mechanic("Mechanic 1", 3);
            
            team.addEngineer(engineer);
            team.addMechanic(mechanic);
            
            assert team.getTeamSize() == 5 : "Team size should be 5";
            assert team.getDrivers().size() == 2 : "Should have 2 drivers";
            assert principal.getBudget() == 1000000.0 : "Budget should be correct";
            
            pass("Team creation");
        } catch (Exception e) {
            fail("Team creation", e.getMessage());
        }
    }
    
    // Тест 2: Побудова болідів
    private static void testCarBuilding() {
        System.out.println("\n--- Test 2: Car Building ---");
        
        try {
            // Builder pattern
            Car car1 = new CarBuilder()
                .focusOnPower()
                .setMaxFuel(110.0)
                .setTireType("Soft")
                .build();
            
            Car car2 = new CarBuilder()
                .focusOnAerodynamics()
                .setMaxFuel(100.0)
                .setTireType("Medium")
                .build();
            
            assert car1.getEnginePower() > 800 : "Power car should have high power";
            assert car2.getAerodynamics() > 8 : "Aero car should have high aero";
            assert car1.getTireType().equals("Soft") : "Should have Soft tires";
            
            // Factory pattern
            Tire tire = TireFactory.createTire("Hard");
            assert tire.getType().equals("Hard") : "Should create Hard tire";
            
            pass("Car building");
        } catch (Exception e) {
            fail("Car building", e.getMessage());
        }
    }
    
    // Тест 3: Шаблони проектування
    private static void testPatterns() {
        System.out.println("\n--- Test 3: Design Patterns ---");
        
        try {
            // Strategy
            Driver driver = new Driver("Test Driver", 9.0, "UK", "Test", 0);
            Car car = new Car(850.0, 8.5, 100.0);
            driver.setCar(car);
            
            driver.setStrategy(new AggressiveStrategy());
            driver.drive();
            
            driver.setStrategy(new BalancedStrategy());
            driver.drive();
            
            // State
            car.checkState();
            assert car.getState() != null : "Car should have state";
            
            // Decorator
            CarComponent engine = new Engine("V6", 800.0);
            CarComponent turbo = new TurboCharger(engine, 50.0);
            assert turbo.getPower() == 850.0 : "Turbo should add power";
            
            // Singleton
            TeamManager manager1 = TeamManager.getInstance();
            TeamManager manager2 = TeamManager.getInstance();
            assert manager1 == manager2 : "Should be same instance";
            
            pass("Design patterns");
        } catch (Exception e) {
            fail("Design patterns", e.getMessage());
        }
    }
    
    // Тест 4: Симуляція гонки
    private static void testRaceSimulation() {
        System.out.println("\n--- Test 4: Race Simulation ---");
        
        try {
            // Створюємо учасників
            Driver d1 = new Driver("Driver 1", 9.5, "UK", "Team 1", 0);
            Driver d2 = new Driver("Driver 2", 9.0, "USA", "Team 2", 0);
            
            Car c1 = new CarBuilder().focusOnPower().build();
            Car c2 = new CarBuilder().focusOnAerodynamics().build();
            
            d1.setCar(c1);
            d2.setCar(c2);
            
            // Створюємо гонку
            Race race = new Race("Test Race", 3);
            ConsoleObserver observer = new ConsoleObserver("TestObserver");
            race.addObserver(observer);
            
            race.addParticipant(d1, c1);
            race.addParticipant(d2, c2);
            
            // Запускаємо гонку
            race.startRace();
            
            // Перевіряємо результати
            List<RaceResult> results = race.getResults();
            assert results.size() == 2 : "Should have 2 results";
            assert !race.isRunning() : "Race should be finished";
            
            // Перевіряємо бали
            ScoringSystem.sortByPosition(results);
            RaceResult winner = results.get(0);
            assert winner.getPoints() > 0 : "Winner should have points";
            
            pass("Race simulation");
        } catch (Exception e) {
            fail("Race simulation", e.getMessage());
        }
    }
    
    // Тест 5: Обробка помилок
    private static void testErrorHandling() {
        System.out.println("\n--- Test 5: Error Handling ---");
        
        try {
            Car car = new Car(800.0, 8.0, 100.0);
            car.setFuelLevel(0);
            
            // Перевіряємо FuelException
            try {
                car.accelerate();
                fail("Error handling", "Should throw FuelException");
            } catch (FuelException e) {
                assert e.getCurrentFuelLevel() == 0 : "Exception should contain fuel level";
                pass("FuelException handling");
            }
            
            // Перевіряємо валідацію
            try {
                Car invalidCar = new Car(1500.0, 8.0, 100.0);
                fail("Error handling", "Should throw exception for invalid power");
            } catch (IllegalArgumentException e) {
                pass("Validation");
            }
            
        } catch (Exception e) {
            fail("Error handling", e.getMessage());
        }
    }
    
    private static void pass(String testName) {
        testCount++;
        passedCount++;
        System.out.println("  [" + testCount + "] " + testName + " [PASSED]");
    }
    
    private static void fail(String testName, String reason) {
        testCount++;
        System.out.println("  [" + testCount + "] " + testName + " [FAILED] - " + reason);
    }
}
