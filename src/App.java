import models.*;
import models.team.*;
import patterns.creational.*;
import patterns.behavioral.*;
import patterns.structural.*;
import exceptions.*;
import java.util.List;
import java.util.Scanner;

// Головний клас програми - інтерфейс користувача
public class App {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        printWelcome();
        
        try {
            int choice = showMenu();
            
            switch (choice) {
                case 1:
                    runFullDemo();
                    break;
                case 2:
                    runCustomTeamDemo();
                    break;
                case 3:
                    runRaceSimulation();
                    break;
                case 4:
                    runPatternsDemo();
                    break;
                case 5:
                    runTeamManagement();
                    break;
                case 0:
                    System.out.println("\nThank you for using Formula 1 Team Simulation!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Running full demo...\n");
                    runFullDemo();
            }
        } catch (Exception e) {
            System.err.println("\n[ERROR] Unexpected error occurred:");
            System.err.println("  " + e.getMessage());
            System.err.println("\nPlease check your input and try again.");
        } finally {
            scanner.close();
        }
    }
    
    private static void printWelcome() {
        System.out.println("============================================================");
        System.out.println("     Formula 1 Team Simulation - OOP Project              ");
        System.out.println("============================================================");
        System.out.println();
        System.out.println("This program demonstrates:");
        System.out.println("  - OOP Principles (Inheritance, Encapsulation, Polymorphism, Abstraction)");
        System.out.println("  - Design Patterns (Creational, Behavioral, Structural)");
        System.out.println("  - GRASP Principles");
        System.out.println("  - Exception Handling");
        System.out.println("  - Race Simulation & Scoring System");
        System.out.println();
    }
    
    private static int showMenu() {
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Run Full Demo (all features)");
        System.out.println("2. Custom Team Creation");
        System.out.println("3. Race Simulation Only");
        System.out.println("4. Design Patterns Demonstration");
        System.out.println("5. Team Management");
        System.out.println("0. Exit");
        System.out.print("\nSelect option (0-5): ");
        
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }
    
    private static void runFullDemo() {
        try {
            System.out.println("\n===========================================================");
            System.out.println("FULL DEMONSTRATION - All Features");
            System.out.println("===========================================================\n");
            
            // Створення команди
            TeamPrincipal principal = new TeamPrincipal("Frederic Vasseur", 15, 2500000.0);
            Team team = new Team("Ferrari F1 Team", principal);
            
            Driver driver1 = team.addDriver("Lewis Hamilton", 9.5, "UK");
            Driver driver2 = team.addDriver("Charles Leclerc", 9.0, "Monaco");
            
            Engineer engineer = new Engineer("James Allison", "Aerodynamics", 20);
            Mechanic mechanic = new Mechanic("John Smith", 10);
            team.addEngineer(engineer);
            team.addMechanic(mechanic);
            
            System.out.println("✓ Team created: " + team.getTeamName());
            System.out.println("✓ Team size: " + team.getTeamSize());
            System.out.println();
            
            // Побудова болідів
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
            
            System.out.println("✓ Cars built and assigned");
            System.out.println();
            
            // Поведінкові шаблони
            driver1.setStrategy(new AggressiveStrategy());
            driver2.setStrategy(new BalancedStrategy());
            car1.checkState();
            
            System.out.println("✓ Strategies and states configured");
            System.out.println();
            
            // Гонка
            Driver driver3 = new Driver("Max Verstappen", 9.8, "Netherlands", "Red Bull", 0);
            Driver driver4 = new Driver("Lando Norris", 9.0, "UK", "McLaren", 0);
            
            Car car3 = new CarBuilder().buildBalanced().setTireType("Soft").build();
            Car car4 = new CarBuilder().focusOnAerodynamics().setTireType("Medium").build();
            
            driver3.setCar(car3);
            driver4.setCar(car4);
            
            Race race = new Race("Monaco Grand Prix", 5);
            ConsoleObserver observer = new ConsoleObserver("RaceFeed");
            race.addObserver(observer);
            
            race.addParticipant(driver1, car1);
            race.addParticipant(driver2, car2);
            race.addParticipant(driver3, car3);
            race.addParticipant(driver4, car4);
            
            System.out.println("--- Starting Race ---\n");
            race.startRace();
            
            System.out.println("\n--- Final Results ---");
            List<RaceResult> results = race.getResults();
            ScoringSystem.sortByPosition(results);
            for (RaceResult result : results) {
                System.out.println(result.toString());
            }
        } catch (IllegalStateException e) {
            System.err.println("\n[ERROR] " + e.getMessage());
        } catch (F1Exception e) {
            System.err.println("\n[ERROR] " + e.getFullErrorInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("\n[ERROR] Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n[ERROR] An error occurred: " + e.getMessage());
        }
    }
    
    private static void runCustomTeamDemo() {
        try {
            System.out.println("\n===========================================================");
            System.out.println("CUSTOM TEAM CREATION");
            System.out.println("===========================================================\n");
            
            System.out.print("Enter team name: ");
            scanner.nextLine(); // consume newline
            String teamName = scanner.nextLine();
            
            System.out.print("Enter principal name: ");
            String principalName = scanner.nextLine();
            
            System.out.print("Enter principal experience (years): ");
            int experience = scanner.nextInt();
            
            System.out.print("Enter budget: ");
            double budget = scanner.nextDouble();
            
            TeamPrincipal principal = new TeamPrincipal(principalName, experience, budget);
            Team team = new Team(teamName, principal);
            
            System.out.println("\n✓ Team created!");
            System.out.println("  Team: " + team.getTeamName());
            System.out.println("  Principal: " + principal.getName());
            System.out.println("  Budget: " + principal.getBudget() + " $");
            System.out.println("  Leadership: " + principal.getLeadership());
            
            // Демонстрація поліморфізму
            System.out.println("\n--- Team Members Working ---");
            for (Driver driver : team.getDrivers()) {
                System.out.print("  " + driver.getName() + ": ");
                driver.work();
            }
            for (Engineer engineer : team.getEngineers()) {
                System.out.print("  " + engineer.getName() + ": ");
                engineer.work();
            }
            for (Mechanic mechanic : team.getMechanics()) {
                System.out.print("  " + mechanic.getName() + ": ");
                mechanic.work();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("\n[ERROR] Invalid input: " + e.getMessage());
            System.err.println("Please check your input and try again.");
        } catch (Exception e) {
            System.err.println("\n[ERROR] An error occurred: " + e.getMessage());
        }
    }
    
    private static void runRaceSimulation() {
        try {
            System.out.println("\n===========================================================");
            System.out.println("RACE SIMULATION");
            System.out.println("===========================================================\n");
            
            // Створення учасників
            Driver d1 = new Driver("Lewis Hamilton", 9.5, "UK", "Mercedes", 0);
            Driver d2 = new Driver("Max Verstappen", 9.8, "Netherlands", "Red Bull", 0);
            Driver d3 = new Driver("Charles Leclerc", 9.0, "Monaco", "Ferrari", 0);
            Driver d4 = new Driver("Lando Norris", 9.0, "UK", "McLaren", 0);
            
            Car c1 = new CarBuilder().focusOnPower().setTireType("Soft").build();
            Car c2 = new CarBuilder().buildBalanced().setTireType("Medium").build();
            Car c3 = new CarBuilder().focusOnAerodynamics().setTireType("Soft").build();
            Car c4 = new CarBuilder().buildBalanced().setTireType("Hard").build();
            
            d1.setCar(c1);
            d2.setCar(c2);
            d3.setCar(c3);
            d4.setCar(c4);
            
            d1.setStrategy(new AggressiveStrategy());
            d2.setStrategy(new AggressiveStrategy());
            d3.setStrategy(new BalancedStrategy());
            d4.setStrategy(new EconomicalStrategy());
            
            // Створення гонки
            Race race = new Race("Silverstone Grand Prix", 10);
            ConsoleObserver observer = new ConsoleObserver("LiveFeed");
            race.addObserver(observer);
            
            race.addParticipant(d1, c1);
            race.addParticipant(d2, c2);
            race.addParticipant(d3, c3);
            race.addParticipant(d4, c4);
            
            System.out.println("Race: " + race.getRaceName());
            System.out.println("Laps: 10");
            System.out.println("Participants: 4");
            System.out.println("\n--- Race Start ---\n");
            
            race.startRace();
            
            System.out.println("\n--- Final Standings ---");
            List<RaceResult> results = race.getResults();
            ScoringSystem.sortByPosition(results);
            for (RaceResult result : results) {
                System.out.println(result.toString());
            }
        } catch (IllegalStateException e) {
            System.err.println("\n[ERROR] " + e.getMessage());
            System.err.println("Please add participants before starting the race.");
        } catch (F1Exception e) {
            System.err.println("\n[ERROR] " + e.getFullErrorInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("\n[ERROR] Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n[ERROR] An error occurred during race: " + e.getMessage());
        }
    }
    
    private static void runPatternsDemo() {
        try {
            System.out.println("\n===========================================================");
            System.out.println("DESIGN PATTERNS DEMONSTRATION");
            System.out.println("===========================================================\n");
            
            // Creational Patterns
            System.out.println("--- Creational Patterns ---");
            Tire tire1 = TireFactory.createTire("Soft");
            Tire tire2 = TireFactory.createTire("Medium");
            System.out.println("Factory Method: Created " + tire1.getType() + " and " + tire2.getType() + " tires");
            
            Car car = new CarBuilder()
                .focusOnPower()
                .setMaxFuel(110.0)
                .setTireType("Soft")
                .build();
            System.out.println("Builder: Created car with " + car.getEnginePower() + " HP");
            
            TeamManager manager1 = TeamManager.getInstance();
            TeamManager manager2 = TeamManager.getInstance();
            System.out.println("Singleton: Same instance? " + (manager1 == manager2));
            System.out.println();
            
            // Behavioral Patterns
            System.out.println("--- Behavioral Patterns ---");
            Driver driver = new Driver("Test Driver", 9.0, "UK", "Test Team", 0);
            driver.setCar(car);
            
            driver.setStrategy(new AggressiveStrategy());
            System.out.print("Strategy (Aggressive): ");
            driver.drive();
            
            driver.setStrategy(new EconomicalStrategy());
            System.out.print("Strategy (Economical): ");
            driver.drive();
            
            car.checkState();
            System.out.println("State Pattern: Car state is " + car.getState().getStateName());
            System.out.println();
            
            // Structural Patterns
            System.out.println("--- Structural Patterns ---");
            CarComponent engine = new Engine("V6", 800.0);
            CarComponent turboEngine = EngineDecoratorFactory.addTurbo(engine);
            CarComponent fullEngine = EngineDecoratorFactory.addERS(turboEngine);
            System.out.println("Decorator: Base " + engine.getPower() + " HP -> " + 
                             fullEngine.getPower() + " HP with upgrades");
            
            LegacyEngine oldEngine = new LegacyEngine("V8", 700);
            CarComponent adapted = new EngineAdapter(oldEngine);
            System.out.println("Adapter: Legacy engine (" + oldEngine.getPower() + " HP) adapted to new interface");
        } catch (IllegalArgumentException e) {
            System.err.println("\n[ERROR] Invalid input: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("\n[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n[ERROR] An error occurred: " + e.getMessage());
        }
    }
    
    private static void runTeamManagement() {
        try {
            System.out.println("\n===========================================================");
            System.out.println("TEAM MANAGEMENT");
            System.out.println("===========================================================");
            System.out.println();
            
            TeamPrincipal principal = new TeamPrincipal("John Principal", 10, 1000000.0);
            Team team = new Team("Custom Team", principal);
            
            System.out.println("Team: " + team.getTeamName());
            System.out.println("Budget: " + principal.getBudget() + " $");
            System.out.println();
            
            // Додавання учасників
            Driver driver = team.addDriver("Test Driver", 8.5, "Test Country");
            Engineer engineer = new Engineer("Test Engineer", "Engine", 5);
            Mechanic mechanic = new Mechanic("Test Mechanic", 3);
            
            team.addEngineer(engineer);
            team.addMechanic(mechanic);
            
            System.out.println("Team Info:");
            System.out.println(team.getTeamInfo());
            System.out.println();
            
            System.out.println("Total Experience: " + team.getTotalExperience() + " years");
            System.out.println("Team Size: " + team.getTeamSize());
            System.out.println();
            
            // Прийняття рішень
            principal.makeStrategicDecision("Invest in aerodynamics");
            principal.allocateBudget(50000, "Car upgrades");
            System.out.println("Remaining budget: " + principal.getBudget() + " $");
        } catch (IllegalArgumentException e) {
            System.err.println("\n[ERROR] Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n[ERROR] An error occurred: " + e.getMessage());
        }
    }
}
