package test;

import models.Team;
import models.Car;
import models.team.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TeamTest {
    
    private static int testCount = 0;
    private static int passedCount = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Testing Team & GRASP Patterns (Stage 7) ===\n");
        
        // Redirect System.out for cleaner output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        
        try {
            System.setOut(new PrintStream(testOutput));
            
            testTeamCreation();
            testGRASPCreator();
            testGRASPInformationExpert();
            testTeamManagement();
            
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
    
    // Test 1: Team Creation
    private static void testTeamCreation() {
        System.out.println("--- Test 1: Team Creation ---");
        
        try {
            TeamPrincipal principal = new TeamPrincipal("Test Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            
            testCount++;
            if (team.getTeamName().equals("Test Team") && team.getPrincipal() == principal) {
                System.out.println("Test 1.1: Team created correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.1: Team creation failed [FAILED]");
            }
            
            testCount++;
            if (team.getTeamSize() == 1) { // Only principal
                System.out.println("Test 1.2: Team size calculated correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.2: Team size should be 1 initially [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 1: Team Creation [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 2: GRASP Creator Pattern
    private static void testGRASPCreator() {
        System.out.println("--- Test 2: GRASP Creator Pattern ---");
        
        try {
            TeamPrincipal principal = new TeamPrincipal("Test Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            
            // Principal creates Driver (Creator pattern)
            Driver driver = team.addDriver("Test Driver", 8.0, "UK");
            
            testCount++;
            if (driver != null && team.getDrivers().size() == 1) {
                System.out.println("Test 2.1: Principal creates Driver (Creator pattern) [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.1: Principal should create Driver [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 2: GRASP Creator [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 3: GRASP Information Expert
    private static void testGRASPInformationExpert() {
        System.out.println("--- Test 3: GRASP Information Expert ---");
        
        try {
            TeamPrincipal principal = new TeamPrincipal("Test Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            team.addDriver("Driver 1", 8.0, "UK");
            team.addDriver("Driver 2", 7.5, "US");
            
            // Team knows about its members (Information Expert)
            testCount++;
            if (team.getTeamSize() == 3) { // principal + 2 drivers
                System.out.println("Test 3.1: Team knows its size (Information Expert) [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.1: Team should know its size [FAILED]");
            }
            
            testCount++;
            int totalExp = team.getTotalExperience();
            if (totalExp >= 10) { // At least principal's experience
                System.out.println("Test 3.2: Team calculates total experience (Information Expert) [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.2: Team should calculate experience [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 3: GRASP Information Expert [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 4: Team Management
    private static void testTeamManagement() {
        System.out.println("--- Test 4: Team Management ---");
        
        try {
            TeamPrincipal principal = new TeamPrincipal("Test Principal", 10, 1000000.0);
            Team team = new Team("Test Team", principal);
            
            // Add members
            team.addDriver("Driver 1", 8.0, "UK");
            Engineer engineer = new Engineer("Engineer 1", "Engine", 5);
            Mechanic mechanic = new Mechanic("Mechanic 1", 3);
            team.addEngineer(engineer);
            team.addMechanic(mechanic);
            
            testCount++;
            if (team.getEngineers().size() == 1 && team.getMechanics().size() == 1) {
                System.out.println("Test 4.1: Team manages members correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 4.1: Team should manage members [FAILED]");
            }
            
            // Set car
            Car car = new Car(800.0, 8.0, 100.0);
            team.setCar(car);
            
            testCount++;
            if (team.getCar() == car && team.getDrivers().get(0).getCar() == car) {
                System.out.println("Test 4.2: Team assigns car to driver [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 4.2: Team should assign car [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 4: Team Management [FAILED] - " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
}
