package models.team;

import utils.Validator;
import static models.team.TeamPrincipalConstants.*;

// Головний команди - керує командою
public class TeamPrincipal extends TeamMember {
    
    private double leadership;
    private int decisionsMade;
    private double budget;
    
    public TeamPrincipal(String name, int experience, double budget) {
        super(name, "Team Principal", experience);
        this.leadership = BASE_LEADERSHIP + (experience * LEADERSHIP_MULTIPLIER);
        if (leadership > MAX_LEADERSHIP) {
            leadership = MAX_LEADERSHIP;
        }
        this.decisionsMade = 0;
        if (budget < 0) {
            this.budget = 0;
        } else {
            this.budget = budget;
        }
    }
    
    public double getLeadership() {
        return leadership;
    }
    
    public int getDecisionsMade() {
        return decisionsMade;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public void setBudget(double budget) {
        if (budget >= 0) {
            this.budget = budget;
        }
    }
    
    @Override
    public void work() {
        System.out.println(name + " manages team and makes strategic decisions");
        decisionsMade++;
        gainExperience();
    }
    
    @Override
    public String getInfo() {
        return String.format("Team Principal: %s, Leadership: %.1f, Decisions: %d, Budget: %.2f, Experience: %d years",
                name, leadership, decisionsMade, budget, experience);
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
    
    public Driver hireDriver(String driverName, double skillLevel, String country) {
        String validName = Validator.validateNonEmpty(driverName, "Driver name");
        System.out.println(this.name + " hires driver: " + validName);
        work();
        return new Driver(validName, skillLevel, country, "Team", 0);
    }
    
    public void makeStrategicDecision(String decision) {
        Validator.validateNonEmpty(decision, "Decision");
        System.out.println(name + " makes decision: " + decision);
        work();
    }
    
    public boolean allocateBudget(double amount, String purpose) {
        if (amount < 0) {
            return false;
        }
        if (budget >= amount) {
            budget -= amount;
            System.out.println(name + " allocates " + amount + " for " + purpose);
            return true;
        }
        System.out.println("Insufficient budget!");
        return false;
    }
}
