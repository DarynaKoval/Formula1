package models.team;

import utils.Validator;
import static models.team.MechanicConstants.*;

// Механік команди
public class Mechanic extends TeamMember {
    
    private int repairsCompleted;
    private double efficiency;
    
    public Mechanic(String name, int experience) {
        super(name, "Mechanic", experience);
        this.repairsCompleted = 0;
        this.efficiency = BASE_EFFICIENCY + (experience * EFFICIENCY_PER_YEAR);
        if (efficiency > MAX_EFFICIENCY) {
            efficiency = MAX_EFFICIENCY;
        }
    }
    
    public int getRepairsCompleted() {
        return repairsCompleted;
    }
    
    public double getEfficiency() {
        return efficiency;
    }
    
    @Override
    public void work() {
        System.out.println(name + " repairs car");
        repairsCompleted++;
        // кожні N ремонтів ефективність росте
        if (repairsCompleted % REPAIRS_FOR_INCREASE == 0 && efficiency < MAX_EFFICIENCY) {
            efficiency += EFFICIENCY_INCREASE;
        }
    }
    
    @Override
    public String getInfo() {
        return String.format("Mechanic: %s, Repairs: %d, Efficiency: %.1f%%, Experience: %d years",
                name, repairsCompleted, efficiency * 100, experience);
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
    
    public boolean performRepair(String issue) {
        try {
            String validIssue = Validator.validateNonEmpty(issue, "Issue");
            System.out.println(name + " fixes issue: " + validIssue);
            work();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    public void performPitStop() {
        System.out.println(name + " performs pit stop");
        work();
    }
}
