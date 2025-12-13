package test;

import models.Team;
import models.team.*;
import patterns.structural.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StructuralPatternsTest {
    
    private static int testCount = 0;
    private static int passedCount = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Testing Structural Patterns (Stage 6) ===\n");
        
        // Redirect System.out for cleaner output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        
        try {
            testDecoratorPattern();
            testAdapterPattern();
            
            System.out.println("\n=== Test Results ===");
            System.out.println("Total tests: " + testCount);
            System.out.println("Passed: " + passedCount);
            System.out.println("Failed: " + (testCount - passedCount));
            
            if (passedCount == testCount) {
                System.out.println("\nALL TESTS PASSED!");
            } else {
                System.out.println("\nSOME TESTS FAILED!");
            }
        } catch (Exception e) {
            System.setOut(originalOut);
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.setOut(originalOut);
        }
    }
    
    // Test 1: Decorator Pattern
    private static void testDecoratorPattern() {
        System.out.println("--- Test 1: Decorator Pattern ---");
        
        try {
            // Create base engine
            CarComponent engine = new Engine("V6 Turbo", 800.0);
            
            // Test base engine
            testCount++;
            if (engine.getPower() == 800.0 && engine.getComponentName().equals("V6 Turbo")) {
                System.out.println("Test 1.1: Base engine created correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.1: Base engine not created correctly [FAILED]");
            }
            
            // Test Turbo decorator
            testCount++;
            CarComponent turboEngine = new TurboCharger(engine, 50.0);
            if (turboEngine.getPower() == 850.0) {
                System.out.println("Test 1.2: Turbo decorator adds power correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.2: Turbo decorator should add 50 HP [FAILED]");
            }
            
            // Test ERS decorator
            testCount++;
            CarComponent ersEngine = new EnergyRecoverySystem(engine, 30.0);
            if (ersEngine.getPower() == 830.0) {
                System.out.println("Test 1.3: ERS decorator adds power correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.3: ERS decorator should add 30 HP [FAILED]");
            }
            
            // Test multiple decorators
            testCount++;
            CarComponent fullEngine = EngineDecoratorFactory.addBoth(engine);
            if (fullEngine.getPower() == 880.0) { // 800 + 50 + 30
                System.out.println("Test 1.4: Multiple decorators work correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.4: Multiple decorators should combine [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 1: Decorator Pattern [FAILED] - " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    // Test 2: Adapter Pattern
    private static void testAdapterPattern() {
        System.out.println("--- Test 2: Adapter Pattern ---");
        
        try {
            // Create legacy engine
            LegacyEngine legacyEngine = new LegacyEngine("Old V8", 700);
            
            // Test adapter
            testCount++;
            CarComponent adaptedEngine = new EngineAdapter(legacyEngine);
            if (adaptedEngine.getPower() == 700.0 && adaptedEngine.getComponentName().equals("Old V8")) {
                System.out.println("Test 2.1: Adapter converts legacy engine correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.1: Adapter should convert int to double [FAILED]");
            }
            
            // Test that adapter can be used with decorators
            testCount++;
            CarComponent turboAdapted = new TurboCharger(adaptedEngine, 50.0);
            if (turboAdapted.getPower() == 750.0) {
                System.out.println("Test 2.2: Adapter works with decorators [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.2: Adapter should work with decorators [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 2: Adapter Pattern [FAILED] - " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
}
