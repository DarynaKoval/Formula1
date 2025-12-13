package test;

import models.team.*;

/**
 * Простий тестовий клас для Етапу 1
 * Демонструє базове тестування без зовнішніх бібліотек
 * 
 * Для запуску: java -ea test.TeamMemberTest
 * (флаг -ea увімкнує assertions)
 */
public class TeamMemberTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Testing Stage 1: Basic Structure and OOP ===\n");
        
        // Тести створення об'єктів
        testDriverCreation();
        testEngineerCreation();
        testMechanicCreation();
        testTeamPrincipalCreation();
        
        // Тести наслідування
        testInheritance();
        
        // Тести поліморфізму
        testPolymorphism();
        
        // Тести інкапсуляції
        testEncapsulation();
        
        // Тести валідації
        testValidation();
        
        // Підсумок
        printSummary();
    }
    
    // Test 1: Creating Driver
    static void testDriverCreation() {
        System.out.println("Test 1: Creating Driver");
        try {
            Driver driver = new Driver("Test Driver", 8.5);
            assert driver.getName().equals("Test Driver") : "Error: wrong name";
            assert driver.getSkillLevel() == 8.5 : "Error: wrong skill level";
            assert driver.getRole().equals("Driver") : "Error: wrong role";
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    // Test 2: Creating Engineer
    static void testEngineerCreation() {
        System.out.println("Test 2: Creating Engineer");
        try {
            Engineer engineer = new Engineer("Test Engineer", "Aerodynamics", 5);
            assert engineer.getName().equals("Test Engineer") : "Error: wrong name";
            assert engineer.getSpecialization().equals("Aerodynamics") : "Error: wrong specialization";
            assert engineer.getExperience() == 5 : "Error: wrong experience";
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    // Test 3: Creating Mechanic
    static void testMechanicCreation() {
        System.out.println("Test 3: Creating Mechanic");
        try {
            Mechanic mechanic = new Mechanic("Test Mechanic", 3);
            assert mechanic.getName().equals("Test Mechanic") : "Error: wrong name";
            assert mechanic.getEfficiency() > 0 : "Error: efficiency must be > 0";
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    // Test 4: Creating TeamPrincipal
    static void testTeamPrincipalCreation() {
        System.out.println("Test 4: Creating TeamPrincipal");
        try {
            TeamPrincipal principal = new TeamPrincipal("Test Principal", 10, 1000000.0);
            assert principal.getName().equals("Test Principal") : "Error: wrong name";
            assert principal.getBudget() == 1000000.0 : "Error: wrong budget";
            assert principal.getLeadership() > 0 : "Error: leadership must be > 0";
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    // Test 5: Inheritance
    static void testInheritance() {
        System.out.println("Test 5: Inheritance from TeamMember");
        try {
            Driver driver = new Driver("Test", 7.0);
            // Check that Driver is TeamMember
            assert driver instanceof TeamMember : "Error: Driver does not extend TeamMember";
            
            // Check usage of base class methods
            int initialExp = driver.getExperience();
            driver.gainExperience();
            assert driver.getExperience() == initialExp + 1 : "Error: gainExperience() does not work";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    // Test 6: Polymorphism
    static void testPolymorphism() {
        System.out.println("Test 6: Polymorphism (different work() implementations)");
        try {
            TeamMember[] team = {
                new Driver("Driver", 8.0),
                new Engineer("Engineer", "Engine", 5),
                new Mechanic("Mechanic", 3),
                new TeamPrincipal("Principal", 10, 500000.0)
            };
            
            // Check that all objects can be stored as TeamMember
            for (TeamMember member : team) {
                assert member instanceof TeamMember : "Error: object is not TeamMember";
                // Check that work() method exists (does not throw error)
                // Вимикаємо вивід для тесту
                java.io.PrintStream originalOut = System.out;
                System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
                    @Override
                    public void write(int b) {
                        // ігноруємо вивід
                    }
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
    
    // Test 7: Encapsulation
    static void testEncapsulation() {
        System.out.println("Test 7: Encapsulation (access through getters)");
        try {
            Driver driver = new Driver("Test", 9.0);
            
            // Check that fields are accessible through getters
            String name = driver.getName();
            double skill = driver.getSkillLevel();
            
            assert name != null : "Error: name cannot be null";
            assert skill >= 1.0 && skill <= 10.0 : "Error: skill out of range";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    // Test 8: Validation
    static void testValidation() {
        System.out.println("Test 8: Parameter validation");
        try {
            // Test skillLevel validation
            boolean exceptionThrown = false;
            try {
                Driver driver = new Driver("Test", 15.0); // invalid value
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assert exceptionThrown : "Error: must throw exception for invalid skillLevel";
            
            // Test name validation
            exceptionThrown = false;
            try {
                TeamMember member = new Driver(null, 5.0); // null name
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assert exceptionThrown : "Error: must throw exception for null name";
            
            System.out.println("[PASSED]\n");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED]: " + e.getMessage() + "\n");
            testsFailed++;
        }
    }
    
    // Print summary
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

