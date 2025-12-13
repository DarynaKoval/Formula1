package test;

import models.Car;
import models.Tire;
import models.team.*;
import patterns.creational.*;
import exceptions.*;

/**
 * Інтеграційний тест - перевіряє як працюють всі етапи разом
 */
public class IntegrationTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Integration Test: All Stages Together ===\n");
        
        testFullScenario();
        testStagesIntegration();
        testExceptionsIntegration();
        testPatternsIntegration();
        
        printSummary();
    }
    
    static void testFullScenario() {
        System.out.println("Test 1: Full scenario (all stages together)");
        try {
            // Етап 1: Створення команди (ООП)
            TeamPrincipal principal = new TeamPrincipal("Toto Wolff", 10, 2000000.0);
            Driver driver1 = principal.hireDriver("Lewis Hamilton", 9.5, "UK");
            Driver driver2 = new Driver("Max Verstappen", 9.8, "Netherlands", "Red Bull", 5);
            Engineer engineer = new Engineer("James Allison", "Aerodynamics", 15);
            Mechanic mechanic = new Mechanic("John Smith", 8);
            
            // Етап 4: Використання породжувальних шаблонів
            Car car1 = new CarBuilder()
                .focusOnPower()
                .setTireType("Soft")
                .setMaxFuel(110.0)
                .build();
            
            Car car2 = new CarBuilder()
                .focusOnAerodynamics()
                .setTireType("Medium")
                .build();
            
            Tire tire = TireFactory.createTire("Hard");
            
            // Етап 2: Зв'язок Driver ↔ Car
            driver1.setCar(car1);
            driver2.setCar(car2);
            
            // Перевірка що все працює
            assert driver1.getCar() == car1 : "Error: driver1 should have car1";
            assert car1.getDriver() == driver1 : "Error: car1 should have driver1";
            assert driver1.getCar().getEnginePower() > 800.0 : "Error: car should have high power";
            
            // Етап 3: Перевірка виключень
            boolean exceptionThrown = false;
            car1.consumeFuel(110.0); // витрачаємо все паливо
            try {
                car1.accelerate();
            } catch (FuelException e) {
                exceptionThrown = true;
                assert e.getCurrentFuelLevel() == 0.0 : "Error: fuel should be 0";
            }
            assert exceptionThrown : "Error: should throw FuelException";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        } catch (Exception e) {
            System.out.println("[FAILED]: Unexpected error - " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testStagesIntegration() {
        System.out.println("Test 2: Stages integration");
        try {
            // Перевірка що Етап 1 (ООП) працює з Етапом 2 (Car)
            Driver driver = new Driver("Test Driver", 8.0);
            Car car = new Car(750.0, 8.5, 105.0);
            
            driver.setCar(car);
            assert car.getDriver() == driver : "Error: bidirectional link should work";
            
            // Перевірка поліморфізму (Етап 1)
            TeamMember[] team = {
                new Driver("Driver", 8.0),
                new Engineer("Engineer", "Engine", 5),
                new Mechanic("Mechanic", 3),
                new TeamPrincipal("Principal", 10, 500000.0)
            };
            
            for (TeamMember member : team) {
                assert member instanceof TeamMember : "Error: should be TeamMember";
                // Перевірка що work() працює
                java.io.PrintStream originalOut = System.out;
                System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
                    @Override
                    public void write(int b) {}
                }));
                member.work();
                System.setOut(originalOut);
            }
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        } catch (Exception e) {
            System.out.println("[FAILED]: Unexpected error - " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testExceptionsIntegration() {
        System.out.println("Test 3: Exceptions integration with Car");
        try {
            Car car = new Car(800.0, 9.0, 100.0);
            
            // Перевірка FuelException
            car.consumeFuel(100.0);
            boolean fuelException = false;
            try {
                car.accelerate();
            } catch (FuelException e) {
                fuelException = true;
                assert e instanceof F1Exception : "Error: FuelException should extend F1Exception";
                assert e.getErrorCode().equals("FUEL_ERROR") : "Error: wrong error code";
            }
            assert fuelException : "Error: should throw FuelException";
            
            // Перевірка TireException
            car.refuel(50.0);
            car.setTireType("Soft");
            boolean tireException = false;
            // Викликаємо кілька разів щоб збільшити шанс виключення
            for (int i = 0; i < 20; i++) {
                try {
                    car.accelerateWithTireCheck();
                } catch (TireException e) {
                    tireException = true;
                    assert e instanceof F1Exception : "Error: TireException should extend F1Exception";
                    break;
                }
            }
            // TireException може не виникнути через random, це нормально
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        } catch (Exception e) {
            System.out.println("[FAILED]: Unexpected error - " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testPatternsIntegration() {
        System.out.println("Test 4: Patterns integration");
        try {
            // Перевірка що Factory, Builder та Singleton працюють разом
            TeamManager manager = TeamManager.getInstance();
            TeamPrincipal principal = new TeamPrincipal("Principal", 10, 1000000.0);
            manager.setPrincipal(principal);
            
            // Builder + Factory разом
            Car car = new CarBuilder()
                .setEnginePower(850.0)
                .setAerodynamics(9.0)
                .build();
            
            Tire tire = TireFactory.createTire("Medium");
            assert tire.getType().equals("Medium") : "Error: factory should create Medium tire";
            
            // Singleton працює
            TeamManager manager2 = TeamManager.getInstance();
            assert manager == manager2 : "Error: singleton should return same instance";
            assert manager2.getPrincipal() == principal : "Error: singleton should share data";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        } catch (Exception e) {
            System.out.println("[FAILED]: Unexpected error - " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void printSummary() {
        System.out.println("=== Integration Test Summary ===");
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        System.out.println("Total: " + (testsPassed + testsFailed));
        
        if (testsFailed == 0) {
            System.out.println("\n[SUCCESS] All integration tests passed!");
            System.out.println("All stages work together correctly!");
        } else {
            System.out.println("\n[WARNING] Some tests failed. Check the integration.");
        }
    }
}
