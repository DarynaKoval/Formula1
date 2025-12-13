package demo;

import models.*;
import models.team.*;
import patterns.creational.*;
import patterns.behavioral.*;
import patterns.structural.*;
import exceptions.*;
import java.util.List;

// Повна демонстрація програми - показує як все працює разом
public class FullDemo {
    
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("     Formula1_OOP - Full Program Demonstration             ");
        System.out.println("============================================================\n");
        
        try {
            // ========== STAGE 1: Creating Team ==========
            System.out.println("===========================================================");
            System.out.println("STAGE 1: Creating Team (OOP Principles)");
            System.out.println("===========================================================\n");
            
            TeamPrincipal principal = new TeamPrincipal("Frederic Vasseur", 15, 2500000.0);
            System.out.println("✓ Created Team Principal: " + principal.getName());
            System.out.println("  Budget: " + principal.getBudget() + " $");
            System.out.println();
            
            Team team = new Team("Ferrari F1 Team", principal);
            System.out.println("✓ Created team: " + team.getTeamName());
            System.out.println();
            
            // Hiring drivers (current Ferrari 2025 lineup)
            Driver driver1 = team.addDriver("Lewis Hamilton", 9.5, "UK");
            Driver driver2 = team.addDriver("Charles Leclerc", 9.0, "Monaco");
            System.out.println("✓ Hired drivers: " + driver1.getName() + ", " + driver2.getName());
            
            // Adding other team members
            Engineer engineer = new Engineer("James Allison", "Aerodynamics", 20);
            Mechanic mechanic = new Mechanic("John Smith", 10);
            team.addEngineer(engineer);
            team.addMechanic(mechanic);
            System.out.println("✓ Added other team members");
            System.out.println();
            
            // Polymorphism demonstration
            System.out.println("--- Polymorphism Demonstration ---");
            TeamMember[] members = {driver1, driver2, engineer, mechanic};
            for (TeamMember member : members) {
                System.out.print("  " + member.getName() + " (" + member.getRole() + "): ");
                member.work();
            }
            System.out.println();
            
            // ========== STAGES 2-4: Building Cars ==========
            System.out.println("===========================================================");
            System.out.println("STAGES 2-4: Building Cars (Creational Patterns)");
            System.out.println("===========================================================\n");
            
            // Using Builder pattern
            Car car1 = new CarBuilder()
                .focusOnPower()
                .setMaxFuel(110.0)
                .setTireType("Soft")
                .build();
            driver1.setCar(car1);
            
            Car car2 = new CarBuilder()
                .focusOnAerodynamics()
                .setMaxFuel(110.0)
                .setTireType("Medium")
                .build();
            driver2.setCar(car2);
            
            System.out.println("✓ Car 1 (Lewis): " + car1.getEnginePower() + " HP, Aero: " + car1.getAerodynamics());
            System.out.println("✓ Car 2 (Charles): " + car2.getEnginePower() + " HP, Aero: " + car2.getAerodynamics());
            System.out.println();
            
            // Using Factory Method for tires
            Tire softTire = TireFactory.createTire("Soft");
            Tire mediumTire = TireFactory.createTire("Medium");
            System.out.println("✓ Created tires: " + softTire.getType() + " (Grip: " + softTire.getGrip() + ")");
            System.out.println("✓ Created tires: " + mediumTire.getType() + " (Grip: " + mediumTire.getGrip() + ")");
            System.out.println();
            
            // Using Singleton
            TeamManager manager = TeamManager.getInstance();
            manager.setPrincipal(principal);
            System.out.println("✓ TeamManager (Singleton) created");
            System.out.println();
            
            // ========== STAGE 5: Behavioral Patterns ==========
            System.out.println("===========================================================");
            System.out.println("STAGE 5: Behavioral Patterns");
            System.out.println("===========================================================\n");
            
            // Strategy Pattern
            driver1.setStrategy(new AggressiveStrategy());
            driver2.setStrategy(new BalancedStrategy());
            System.out.println("✓ Lewis uses Aggressive strategy");
            System.out.println("✓ Charles uses Balanced strategy");
            System.out.println();
            
            // State Pattern
            car1.checkState();
            System.out.println("✓ Car state (Lewis): " + car1.getState().getStateName());
            System.out.println();
            
            // ========== STAGE 6: Structural Patterns ==========
            System.out.println("===========================================================");
            System.out.println("STAGE 6: Structural Patterns");
            System.out.println("===========================================================\n");
            
            // Decorator Pattern
            CarComponent baseEngine = new Engine("V6 Turbo", 800.0);
            CarComponent turboEngine = new TurboCharger(baseEngine, 50.0);
            CarComponent fullEngine = new EnergyRecoverySystem(turboEngine, 30.0);
            System.out.println("✓ Base engine: " + baseEngine.getPower() + " HP");
            System.out.println("✓ With turbo: " + turboEngine.getPower() + " HP");
            System.out.println("✓ With turbo + ERS: " + fullEngine.getPower() + " HP");
            System.out.println();
            
            // Adapter Pattern
            LegacyEngine oldEngine = new LegacyEngine("Old V8", 700);
            CarComponent adaptedEngine = new EngineAdapter(oldEngine);
            System.out.println("✓ Adapted legacy engine: " + adaptedEngine.getPower() + " HP");
            System.out.println();
            
            // ========== STAGES 8-9: Race Simulation ==========
            System.out.println("===========================================================");
            System.out.println("STAGES 8-9: Race Simulation & Scoring System");
            System.out.println("===========================================================\n");
            
            // Creating additional drivers for race (other teams)
            Driver driver3 = new Driver("Max Verstappen", 9.8, "Netherlands", "Red Bull", 0);
            Driver driver4 = new Driver("Lando Norris", 9.0, "UK", "McLaren", 0);
            
            Car car3 = new CarBuilder().buildBalanced().setTireType("Soft").build();
            Car car4 = new CarBuilder().focusOnAerodynamics().setTireType("Medium").build();
            
            driver3.setCar(car3);
            driver4.setCar(car4);
            driver3.setStrategy(new AggressiveStrategy());
            driver4.setStrategy(new BalancedStrategy());
            
            // Creating race
            Race race = new Race("Monaco Grand Prix", 5); // 5 laps for demonstration
            
            // Adding observers
            ConsoleObserver consoleObserver = new ConsoleObserver("RaceFeed");
            LoggingObserver logObserver = new LoggingObserver("Logger");
            race.addObserver(consoleObserver);
            race.addObserver(logObserver);
            
            // Adding participants
            race.addParticipant(driver1, car1);
            race.addParticipant(driver2, car2);
            race.addParticipant(driver3, car3);
            race.addParticipant(driver4, car4);
            
            System.out.println("✓ Created race: " + race.getRaceName());
            System.out.println("✓ Number of laps: 5");
            System.out.println("✓ Participants: " + race.getResults().size());
            System.out.println();
            
            // Starting race
            System.out.println("--- Starting Race ---\n");
            race.startRace();
            
            // Displaying results
            System.out.println("\n--- Race Results ---");
            List<RaceResult> results = race.getResults();
            ScoringSystem.sortByPosition(results);
            
            for (RaceResult result : results) {
                System.out.println(result.toString());
            }
            
            // Points calculation
            System.out.println("\n--- Points Distribution ---");
            for (RaceResult result : results) {
                System.out.println(result.getDriver().getName() + ": " + result.getPoints() + " points (P" + result.getPosition() + ")");
            }
            
            // ========== SUMMARY ==========
            System.out.println("\n===========================================================");
            System.out.println("SUMMARY");
            System.out.println("===========================================================");
            System.out.println("✓ Team created and ready for racing");
            System.out.println("✓ All OOP principles applied");
            System.out.println("✓ All design patterns implemented");
            System.out.println("✓ Race successfully completed");
            System.out.println("✓ Points distributed");
            System.out.println("\nProgram works correctly! ✓\n");
            
        } catch (F1Exception e) {
            System.err.println("\n[ERROR] Formula 1 Error occurred:");
            System.err.println("  " + e.getFullErrorInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("\n[ERROR] Invalid input:");
            System.err.println("  " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n[ERROR] Unexpected error occurred:");
            System.err.println("  " + e.getMessage());
            System.err.println("\nPlease check the configuration and try again.");
        }
    }
}
