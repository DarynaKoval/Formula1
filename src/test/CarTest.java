package test;

import models.Car;
import models.team.Driver;
import exceptions.FuelException;

/**
 * Тестовий клас для Етапу 2: Модель боліда
 */
public class CarTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Testing Stage 2: Car Model ===\n");
        
        testCarCreation();
        testCarMethods();
        testCarDriverAssociation();
        testValidation();
        
        printSummary();
    }
    
    static void testCarCreation() {
        System.out.println("Test 1: Creating Car");
        try {
            Car car = new Car(750.0, 8.5, 110.0);
            assert car.getEnginePower() == 750.0 : "Error: wrong engine power";
            assert car.getAerodynamics() == 8.5 : "Error: wrong aerodynamics";
            assert car.getMaxFuel() == 110.0 : "Error: wrong max fuel";
            assert car.getFuelLevel() == 110.0 : "Error: fuel should be full at start";
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testCarMethods() {
        System.out.println("Test 2: Car methods (accelerate, brake, refuel)");
        java.io.PrintStream originalOut = System.out;
        try {
            Car car = new Car(800.0, 9.0, 100.0);
            double initialFuel = car.getFuelLevel();
            
            // Приховуємо вивід під час тестування
            System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
                @Override
                public void write(int b) {
                    // ігноруємо вивід
                }
            }));
            
            // Тест прискорення
            car.accelerate();
            assert car.getFuelLevel() < initialFuel : "Error: fuel should decrease after acceleration";
            
            // Тест заправки
            double fuelBeforeRefuel = car.getFuelLevel();
            car.refuel(20.0);
            assert car.getFuelLevel() > fuelBeforeRefuel : "Error: fuel should increase after refuel";
            assert car.getFuelLevel() <= car.getMaxFuel() : "Error: fuel cannot exceed max fuel";
            
            // Повертаємо вивід
            System.setOut(originalOut);
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.setOut(originalOut);
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        } catch (Exception e) {
            System.setOut(originalOut);
            System.out.println("[FAILED]: Unexpected error - " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testCarDriverAssociation() {
        System.out.println("Test 3: Car-Driver association");
        try {
            Car car = new Car(700.0, 7.5, 105.0);
            Driver driver = new Driver("Test Driver", 8.5);
            
            // Встановлюємо зв'язок
            driver.setCar(car);
            
            assert driver.getCar() == car : "Error: driver should have car";
            assert car.getDriver() == driver : "Error: car should have driver";
            assert car.getDriver().getName().equals("Test Driver") : "Error: wrong driver name";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testValidation() {
        System.out.println("Test 4: Validation");
        try {
            // Тест невалідної потужності двигуна
            boolean exceptionThrown = false;
            try {
                Car car = new Car(1500.0, 8.0, 100.0); // занадто висока потужність
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assert exceptionThrown : "Error: should throw exception for invalid engine power";
            
            // Тест невалідної аеродинаміки
            exceptionThrown = false;
            try {
                Car car = new Car(750.0, 15.0, 100.0); // занадто висока аеродинаміка
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assert exceptionThrown : "Error: should throw exception for invalid aerodynamics";
            
            // Тест невалідного типу шин
            exceptionThrown = false;
            try {
                Car car = new Car(750.0, 8.0, 100.0);
                car.setTireType("Invalid");
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assert exceptionThrown : "Error: should throw exception for invalid tire type";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void printSummary() {
        System.out.println("=== Test Summary ===");
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        System.out.println("Total: " + (testsPassed + testsFailed));
        
        if (testsFailed == 0) {
            System.out.println("\n[SUCCESS] All tests passed!");
        } else {
            System.out.println("\n[WARNING] Some tests failed. Check the code.");
        }
    }
}

