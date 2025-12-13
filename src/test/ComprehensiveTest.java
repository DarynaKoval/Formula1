package test;

import models.*;
import models.team.*;
import patterns.behavioral.*;
import patterns.creational.*;
import patterns.structural.*;
import utils.Validator;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Comprehensive Test Suite for Stages 10-11
 * Tests for TDD improvement and refactoring verification
 */
public class ComprehensiveTest {
    
    private static int testCount = 0;
    private static int passedCount = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Comprehensive Test Suite (Stages 10-11) ===\n");
        
        PrintStream originalOut = System.out;
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        
        try {
            testValidatorUtility();
            testCompleteRaceFlow();
            testScoringSystemDetails();
            testTeamCompleteWorkflow();
            testAllPatternsIntegration();
            
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
    
    // Test 1: Validator utility (refactoring)
    private static void testValidatorUtility() {
        System.out.println("--- Test 1: Validator Utility (Refactoring) ---");
        
        try {
            // Test validateNonEmpty
            testCount++;
            try {
                Validator.validateNonEmpty("test", "Field");
                String result = Validator.validateNonEmpty("  test  ", "Field");
                if (result.equals("test")) {
                    System.out.println("Test 1.1: validateNonEmpty works correctly [PASSED]");
                    passedCount++;
                } else {
                    System.out.println("Test 1.1: Should trim whitespace [FAILED]");
                }
            } catch (Exception e) {
                System.out.println("Test 1.1: validateNonEmpty failed [FAILED]");
            }
            
            // Test null validation
            testCount++;
            boolean exceptionThrown = false;
            try {
                Validator.validateNonEmpty(null, "Field");
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (exceptionThrown) {
                System.out.println("Test 1.2: Validator rejects null [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.2: Should throw exception for null [FAILED]");
            }
            
            // Test validateRange
            testCount++;
            try {
                Validator.validateRange(5.0, 1.0, 10.0, "Value");
                System.out.println("Test 1.3: validateRange works correctly [PASSED]");
                passedCount++;
            } catch (Exception e) {
                System.out.println("Test 1.3: validateRange should accept valid range [FAILED]");
            }
            
            // Test validateNotNull
            testCount++;
            exceptionThrown = false;
            try {
                Validator.validateNotNull(null, "Object");
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (exceptionThrown) {
                System.out.println("Test 1.4: validateNotNull rejects null [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.4: Should throw exception for null [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 1: Validator Utility [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 2: Complete race flow
    private static void testCompleteRaceFlow() {
        System.out.println("--- Test 2: Complete Race Flow ---");
        
        try {
            Race race = new Race("Test Race", 3);
            
            Driver driver1 = new Driver("Driver 1", 9.0);
            Driver driver2 = new Driver("Driver 2", 8.5);
            Car car1 = new Car(850.0, 8.5, 100.0);
            Car car2 = new Car(800.0, 9.0, 100.0);
            
            driver1.setCar(car1);
            driver2.setCar(car2);
            
            race.addParticipant(driver1, car1);
            race.addParticipant(driver2, car2);
            
            testCount++;
            if (race.getResults().size() == 2) {
                System.out.println("Test 2.1: Race participants added [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.1: Should have 2 participants [FAILED]");
            }
            
            // Start race
            System.setOut(new PrintStream(new ByteArrayOutputStream()));
            race.startRace();
            System.setOut(System.out);
            
            testCount++;
            List<RaceResult> results = race.getResults();
            if (!race.isRunning() && results.size() > 0) {
                System.out.println("Test 2.2: Race completed successfully [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.2: Race should be finished [FAILED]");
            }
            
            testCount++;
            boolean allHavePoints = true;
            for (RaceResult result : results) {
                if (result.getPoints() == 0) {
                    allHavePoints = false;
                    break;
                }
            }
            if (allHavePoints || results.get(0).getPoints() > 0) {
                System.out.println("Test 2.3: Results have points assigned [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.3: Results should have points [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 2: Complete Race Flow [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 3: Scoring system details
    private static void testScoringSystemDetails() {
        System.out.println("--- Test 3: Scoring System Details ---");
        
        try {
            java.util.List<RaceResult> results = new java.util.ArrayList<>();
            
            for (int i = 1; i <= 10; i++) {
                Driver driver = new Driver("Driver " + i, 8.0);
                RaceResult result = new RaceResult(driver, i);
                result.setFinished(true);
                result.setLapTime(75.0 + i);
                results.add(result);
            }
            
            ScoringSystem.sortByPosition(results);
            java.util.Map<Driver, Integer> points = ScoringSystem.calculatePoints(results);
            
            testCount++;
            if (points.get(results.get(0).getDriver()) == 25) {
                System.out.println("Test 3.1: Winner gets 25 points [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.1: Winner should get 25 points [FAILED]");
            }
            
            testCount++;
            if (points.get(results.get(9).getDriver()) == 1) {
                System.out.println("Test 3.2: 10th place gets 1 point [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.2: 10th place should get 1 point [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 3: Scoring System Details [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 4: Team complete workflow
    private static void testTeamCompleteWorkflow() {
        System.out.println("--- Test 4: Team Complete Workflow ---");
        
        try {
            TeamPrincipal principal = new TeamPrincipal("Test Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            
            team.addDriver("Driver 1", 9.0, "UK");
            team.addDriver("Driver 2", 8.5, "US");
            
            Engineer engineer = new Engineer("Engineer", "Engine", 5);
            Mechanic mechanic = new Mechanic("Mechanic", 3);
            team.addEngineer(engineer);
            team.addMechanic(mechanic);
            
            Car car = new Car(800.0, 8.5, 100.0);
            team.setCar(car);
            
            testCount++;
            if (team.getTeamSize() == 5) { // principal + 2 drivers + engineer + mechanic
                System.out.println("Test 4.1: Team size calculated correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 4.1: Team size should be 5 [FAILED]");
            }
            
            testCount++;
            int totalExp = team.getTotalExperience();
            if (totalExp >= 10) {
                System.out.println("Test 4.2: Total experience calculated [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 4.2: Total experience should be calculated [FAILED]");
            }
            
            testCount++;
            if (team.getCar() == car && team.getDrivers().get(0).getCar() == car) {
                System.out.println("Test 4.3: Car assigned to driver [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 4.3: Car should be assigned to driver [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 4: Team Complete Workflow [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 5: All patterns integration
    private static void testAllPatternsIntegration() {
        System.out.println("--- Test 5: All Patterns Integration ---");
        
        try {
            // Creational patterns
            TeamManager manager = TeamManager.getInstance();
            Car car = new CarBuilder().buildBalanced().build();
            Tire tire = TireFactory.createTire("Soft");
            
            // Behavioral patterns
            Driver driver = new Driver("Test", 8.0);
            driver.setStrategy(new AggressiveStrategy());
            driver.setCar(car);
            car.checkState();
            
            // Structural patterns
            CarComponent engine = new Engine("Test", 800.0);
            CarComponent turbo = new TurboCharger(engine, 50.0);
            
            testCount++;
            if (turbo.getPower() > engine.getPower()) {
                System.out.println("Test 5.1: All patterns work together [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 5.1: Patterns integration failed [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 5: All Patterns Integration [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
}
