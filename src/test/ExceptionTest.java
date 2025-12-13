package test;

import exceptions.*;
import models.Car;

/**
 * Тестовий клас для Етапу 3: Ієрархія виключень
 */
public class ExceptionTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Testing Stage 3: Exception Hierarchy ===\n");
        
        testExceptionHierarchy();
        testFuelException();
        testTireException();
        testRaceException();
        testExceptionUsage();
        
        printSummary();
    }
    
    static void testExceptionHierarchy() {
        System.out.println("Test 1: Exception hierarchy");
        try {
            // Перевірка що всі виключення наслідуються від F1Exception
            F1Exception fuelEx = new FuelException("Test");
            F1Exception tireEx = new TireException("Test");
            F1Exception raceEx = new RaceException("Test");
            
            assert fuelEx instanceof F1Exception : "FuelException should extend F1Exception";
            assert tireEx instanceof F1Exception : "TireException should extend F1Exception";
            assert raceEx instanceof F1Exception : "RaceException should extend F1Exception";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testFuelException() {
        System.out.println("Test 2: FuelException");
        try {
            FuelException ex = new FuelException("Out of fuel", 5.5);
            
            assert ex.getErrorCode().equals("FUEL_ERROR") : "Error: wrong error code";
            assert ex.getCurrentFuelLevel() == 5.5 : "Error: wrong fuel level";
            assert !ex.getFullErrorInfo().isEmpty() : "Error: full error info should not be empty";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testTireException() {
        System.out.println("Test 3: TireException");
        try {
            TireException ex = new TireException("Tire puncture", "Soft", "puncture");
            
            assert ex.getErrorCode().equals("TIRE_ERROR") : "Error: wrong error code";
            assert ex.getTireType().equals("Soft") : "Error: wrong tire type";
            assert ex.getIssueType().equals("puncture") : "Error: wrong issue type";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testRaceException() {
        System.out.println("Test 4: RaceException");
        try {
            RaceException ex = new RaceException("Accident on track", "race");
            
            assert ex.getErrorCode().equals("RACE_ERROR") : "Error: wrong error code";
            assert ex.getRacePhase().equals("race") : "Error: wrong race phase";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testExceptionUsage() {
        System.out.println("Test 5: Exception usage in Car");
        try {
            Car car = new Car(800.0, 9.0, 100.0);
            
            // Витрачаємо все паливо
            car.consumeFuel(100.0);
            
            // Спроба прискорити без палива має викинути виключення
            boolean exceptionThrown = false;
            try {
                car.accelerate();
            } catch (FuelException e) {
                exceptionThrown = true;
                assert e.getCurrentFuelLevel() == 0.0 : "Error: fuel level should be 0";
            }
            assert exceptionThrown : "Error: should throw FuelException when no fuel";
            
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
