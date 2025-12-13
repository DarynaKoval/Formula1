package test;

import models.*;
import models.team.*;
import patterns.behavioral.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RaceAndScoringTest {
    
    private static int testCount = 0;
    private static int passedCount = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Testing Race Simulation & Scoring (Stages 8-9) ===\n");
        
        // Redirect System.out for cleaner output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        
        try {
            System.setOut(new PrintStream(testOutput));
            
            testRaceResult();
            testScoringSystem();
            testRaceSimulation();
            
            System.setOut(originalOut);
            
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
    
    // Test 1: RaceResult
    private static void testRaceResult() {
        System.out.println("--- Test 1: RaceResult ---");
        
        try {
            Driver driver = new Driver("Test Driver", 8.0);
            RaceResult result = new RaceResult(driver, 1);
            
            testCount++;
            if (result.getDriver() == driver && result.getPosition() == 1) {
                System.out.println("Test 1.1: RaceResult created correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.1: RaceResult creation failed [FAILED]");
            }
            
            result.setLapTime(85.5);
            result.setPoints(25);
            result.setFinished(true);
            result.incrementLaps();
            
            testCount++;
            if (result.getLapTime() == 85.5 && result.getPoints() == 25 && 
                result.isFinished() && result.getLapsCompleted() == 1) {
                System.out.println("Test 1.2: RaceResult setters work correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.2: RaceResult setters failed [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 1: RaceResult [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 2: ScoringSystem
    private static void testScoringSystem() {
        System.out.println("--- Test 2: ScoringSystem ---");
        
        try {
            Driver driver1 = new Driver("Driver 1", 9.0);
            Driver driver2 = new Driver("Driver 2", 8.5);
            Driver driver3 = new Driver("Driver 3", 8.0);
            
            RaceResult result1 = new RaceResult(driver1, 1);
            RaceResult result2 = new RaceResult(driver2, 2);
            RaceResult result3 = new RaceResult(driver3, 3);
            
            result1.setFinished(true);
            result2.setFinished(true);
            result3.setFinished(true);
            
            java.util.List<RaceResult> results = new java.util.ArrayList<>();
            results.add(result1);
            results.add(result2);
            results.add(result3);
            
            ScoringSystem.sortByPosition(results);
            java.util.Map<Driver, Integer> points = ScoringSystem.calculatePoints(results);
            
            testCount++;
            if (points.get(driver1) == 25 && points.get(driver2) == 18 && points.get(driver3) == 15) {
                System.out.println("Test 2.1: Points calculated correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.1: Points calculation failed [FAILED]");
            }
            
            testCount++;
            int pointsFor1st = ScoringSystem.getPointsForPosition(1);
            if (pointsFor1st == 25) {
                System.out.println("Test 2.2: Points for position 1 correct [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.2: Points for position should be 25 [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 2: ScoringSystem [FAILED] - " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    // Test 3: Race Simulation
    private static void testRaceSimulation() {
        System.out.println("--- Test 3: Race Simulation ---");
        
        try {
            // Create drivers and cars
            Driver driver1 = new Driver("Lewis Hamilton", 9.5);
            Driver driver2 = new Driver("Max Verstappen", 9.8);
            
            Car car1 = new Car(850.0, 8.5, 100.0);
            Car car2 = new Car(880.0, 9.0, 100.0);
            
            // Create race
            Race race = new Race("Test Grand Prix", 5); // 5 laps for testing
            
            testCount++;
            if (race.getRaceName().equals("Test Grand Prix") && !race.isRunning()) {
                System.out.println("Test 3.1: Race created correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.1: Race creation failed [FAILED]");
            }
            
            // Add participants
            race.addParticipant(driver1, car1);
            race.addParticipant(driver2, car2);
            
            testCount++;
            if (race.getResults().size() == 2) {
                System.out.println("Test 3.2: Participants added correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.2: Should have 2 participants [FAILED]");
            }
            
            // Add observer
            ConsoleObserver observer = new ConsoleObserver("TestObserver");
            race.addObserver(observer);
            
            // Start race (this will run simulation)
            race.startRace();
            
            testCount++;
            if (!race.isRunning() && race.getResults().size() == 2) {
                System.out.println("Test 3.3: Race simulation completed [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.3: Race should be finished [FAILED]");
            }
            
            // Check that results have points
            testCount++;
            boolean allHavePoints = true;
            for (RaceResult result : race.getResults()) {
                if (result.getPoints() == 0) {
                    allHavePoints = false;
                    break;
                }
            }
            if (allHavePoints || race.getResults().get(0).getPoints() > 0) {
                System.out.println("Test 3.4: Results have points assigned [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.4: Results should have points [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 3: Race Simulation [FAILED] - " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
}
