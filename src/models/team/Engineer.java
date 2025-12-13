package models.team;

import utils.Validator;

// Інженер команди
public class Engineer extends TeamMember {
    
    private String specialization;
    private int projectsCompleted;
    
    public Engineer(String name, String specialization, int experience) {
        super(name, "Engineer", experience);
        try {
            this.specialization = Validator.validateNonEmpty(specialization, "Specialization");
        } catch (IllegalArgumentException e) {
            this.specialization = "General";
        }
        this.projectsCompleted = 0;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public int getProjectsCompleted() {
        return projectsCompleted;
    }
    
    public void setSpecialization(String specialization) {
        try {
            this.specialization = Validator.validateNonEmpty(specialization, "Specialization");
        } catch (IllegalArgumentException e) {
            // залишаємо поточну спеціалізацію
        }
    }
    
    @Override
    public void work() {
        System.out.println(name + " works on improving " + specialization);
        projectsCompleted++;
    }
    
    @Override
    public String getInfo() {
        return String.format("Engineer: %s, Specialization: %s, Projects: %d, Experience: %d years",
                name, specialization, projectsCompleted, experience);
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
    
    public void improveCarComponent(String component) {
        try {
            String validComponent = Validator.validateNonEmpty(component, "Component");
            System.out.println(name + " improves component: " + validComponent);
            work();
        } catch (IllegalArgumentException e) {
            // ігноруємо помилку
        }
    }
}
