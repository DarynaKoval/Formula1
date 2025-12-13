package test;

import patterns.creational.*;
import models.Tire;
import models.Car;

/**
 * Тестовий клас для Етапу 4: Породжувальні шаблони
 */
public class CreationalPatternsTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Testing Stage 4: Creational Patterns ===\n");
        
        testTireFactory();
        testCarBuilder();
        testTeamManagerSingleton();
        
        printSummary();
    }
    
    static void testTireFactory() {
        System.out.println("Test 1: TireFactory (Factory Method)");
        try {
            // Тест створення різних типів шин
            Tire soft = TireFactory.createTire("Soft");
            Tire medium = TireFactory.createTire("Medium");
            Tire hard = TireFactory.createTire("Hard");
            
            assert soft.getType().equals("Soft") : "Error: wrong tire type";
            assert soft.getGrip() > medium.getGrip() : "Error: Soft should have higher grip";
            assert hard.getDurability() > soft.getDurability() : "Error: Hard should have higher durability";
            
            // Тест невалідного типу
            boolean exceptionThrown = false;
            try {
                TireFactory.createTire("Invalid");
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assert exceptionThrown : "Error: should throw exception for invalid type";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testCarBuilder() {
        System.out.println("Test 2: CarBuilder (Builder)");
        try {
            // Тест побудови з параметрами
            Car car1 = new CarBuilder()
                .setEnginePower(850.0)
                .setAerodynamics(9.0)
                .setMaxFuel(115.0)
                .setTireType("Soft")
                .build();
            
            assert car1.getEnginePower() == 850.0 : "Error: wrong engine power";
            assert car1.getAerodynamics() == 9.0 : "Error: wrong aerodynamics";
            assert car1.getTireType().equals("Soft") : "Error: wrong tire type";
            
            // Тест методів focusOnPower та focusOnAerodynamics
            Car car2 = new CarBuilder()
                .focusOnPower()
                .build();
            
            assert car2.getEnginePower() > 850.0 : "Error: focusOnPower should increase power";
            
            Car car3 = new CarBuilder()
                .focusOnAerodynamics()
                .build();
            
            assert car3.getAerodynamics() > 9.0 : "Error: focusOnAerodynamics should increase aero";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    static void testTeamManagerSingleton() {
        System.out.println("Test 3: TeamManager (Singleton)");
        try {
            // Тест що getInstance() повертає той самий об'єкт
            TeamManager manager1 = TeamManager.getInstance();
            TeamManager manager2 = TeamManager.getInstance();
            
            assert manager1 == manager2 : "Error: should return the same instance";
            assert manager1.equals(manager2) : "Error: should be the same object";
            
            // Тест що можна встановлювати та отримувати дані
            models.team.TeamPrincipal principal = new models.team.TeamPrincipal("Test", 5, 500000.0);
            manager1.setPrincipal(principal);
            
            TeamManager manager3 = TeamManager.getInstance();
            assert manager3.getPrincipal() == principal : "Error: should share the same data";
            
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

