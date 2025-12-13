package test;

import models.Car;
import models.team.Driver;
import patterns.behavioral.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BehavioralPatternsTest {
    
    private static int testCount = 0;
    private static int passedCount = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Testing Behavioral Patterns (Stage 5) ===\n");
        
        // Redirect System.out for cleaner output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        
        try {
            testStrategyPattern();
            testStatePattern();
            testObserverPattern();
            
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
    
    // Test 1: Strategy Pattern
    private static void testStrategyPattern() {
        System.out.println("--- Test 1: Strategy Pattern ---");
        
        try {
            // Create driver and car
            Driver driver = new Driver("Test Driver", 8.0);
            Car car = new Car(850.0, 8.5, 100.0);
            driver.setCar(car);
            
            // Test default strategy (Balanced)
            testCount++;
            String defaultStrategy = driver.getStrategy().getStrategyName();
            if (defaultStrategy.equals("Balanced")) {
                System.out.println("Test 1.1: Default strategy is Balanced [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.1: Default strategy should be Balanced [FAILED]");
            }
            
            // Test Aggressive Strategy
            testCount++;
            driver.setStrategy(new AggressiveStrategy());
            if (driver.getStrategy().getStrategyName().equals("Aggressive")) {
                System.out.println("Test 1.2: Aggressive strategy set correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.2: Aggressive strategy not set correctly [FAILED]");
            }
            
            // Test Economical Strategy
            testCount++;
            driver.setStrategy(new EconomicalStrategy());
            if (driver.getStrategy().getStrategyName().equals("Economical")) {
                System.out.println("Test 1.3: Economical strategy set correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.3: Economical strategy not set correctly [FAILED]");
            }
            
            // Test drive() method with strategy
            testCount++;
            double initialFuel = car.getFuelLevel();
            driver.drive();
            double fuelAfterDrive = car.getFuelLevel();
            if (fuelAfterDrive < initialFuel) {
                System.out.println("Test 1.4: Drive method uses strategy correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 1.4: Drive method should consume fuel [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 1: Strategy Pattern [FAILED] - " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test 2: State Pattern
    private static void testStatePattern() {
        System.out.println("--- Test 2: State Pattern ---");
        
        try {
            Car car = new Car(850.0, 8.5, 100.0);
            
            // Test initial state (Normal)
            testCount++;
            String initialState = car.getState().getStateName();
            if (initialState.equals("Normal")) {
                System.out.println("Test 2.1: Initial state is Normal [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.1: Initial state should be Normal [FAILED]");
            }
            
            // Test setting different states
            testCount++;
            car.setState(new OptimalState());
            if (car.getState().getStateName().equals("Optimal")) {
                System.out.println("Test 2.2: Optimal state set correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.2: Optimal state not set correctly [FAILED]");
            }
            
            testCount++;
            car.setState(new OverheatingState());
            if (car.getState().getStateName().equals("Overheating")) {
                System.out.println("Test 2.3: Overheating state set correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.3: Overheating state not set correctly [FAILED]");
            }
            
            testCount++;
            car.setState(new CriticalState());
            if (car.getState().getStateName().equals("Critical")) {
                System.out.println("Test 2.4: Critical state set correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.4: Critical state not set correctly [FAILED]");
            }
            
            // Test checkState() method
            testCount++;
            car.setFuelLevel(5.0); // Low fuel - should trigger Critical
            car.checkState();
            String stateAfterCheck = car.getState().getStateName();
            if (stateAfterCheck.equals("Critical")) {
                System.out.println("Test 2.5: checkState() detects Critical state [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 2.5: checkState() should detect Critical state [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 2: State Pattern [FAILED] - " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    // Test 3: Observer Pattern
    private static void testObserverPattern() {
        System.out.println("--- Test 3: Observer Pattern ---");
        
        try {
            RaceSession session = new RaceSession("Test Race");
            
            // Create observers
            ConsoleObserver consoleObserver = new ConsoleObserver("Console");
            LoggingObserver loggingObserver = new LoggingObserver("Logger");
            EventCounterObserver counterObserver = new EventCounterObserver("Counter");
            
            // Test attaching observers
            testCount++;
            session.attach(consoleObserver);
            session.attach(loggingObserver);
            session.attach(counterObserver);
            System.out.println("Test 3.1: Observers attached successfully [PASSED]");
            passedCount++;
            
            // Test notifying observers
            testCount++;
            int initialCount = counterObserver.getEventCount();
            session.startRace();
            if (counterObserver.getEventCount() > initialCount) {
                System.out.println("Test 3.2: Observers notified correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.2: Observers should be notified [FAILED]");
            }
            
            // Test logging observer
            testCount++;
            session.addEvent("Test event");
            String log = loggingObserver.getLog();
            if (log != null && log.contains("Test event")) {
                System.out.println("Test 3.3: LoggingObserver logs events correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.3: LoggingObserver should log events [FAILED]");
            }
            
            // Test detaching observer
            testCount++;
            int countBefore = counterObserver.getEventCount();
            session.detach(counterObserver);
            session.finishRace();
            int countAfter = counterObserver.getEventCount();
            if (countAfter == countBefore) {
                System.out.println("Test 3.4: Observer detached correctly [PASSED]");
                passedCount++;
            } else {
                System.out.println("Test 3.4: Detached observer should not be notified [FAILED]");
            }
            
        } catch (Exception e) {
            System.out.println("Test 3: Observer Pattern [FAILED] - " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
}

