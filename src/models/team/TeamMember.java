package models.team;

import utils.Validator;

// Базовий клас для членів команди
// Абстрактний - не можна створити об'єкт напряму
public abstract class TeamMember {
    
    protected String name;
    protected String role;
    protected int experience;
    
    public TeamMember(String name, String role, int experience) {
        setName(name);
        this.role = (role != null) ? role : "Team Member";
        if (experience < 0) {
            this.experience = 0;
        } else {
            this.experience = experience;
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getRole() {
        return role;
    }
    
    public int getExperience() {
        return experience;
    }
    
    public void setName(String name) {
        this.name = Validator.validateNonEmpty(name, "Name");
    }
    
    public void setRole(String role) {
        if (role != null) {
            this.role = role;
        }
    }
    
    public void setExperience(int experience) {
        if (experience >= 0) {
            this.experience = experience;
        }
    }
    
    // Абстрактний метод - кожен підклас реалізує по-своєму (поліморфізм)
    public abstract void work();
    
    // Абстрактний метод для інформації про члена команди
    public abstract String getInfo();
    
    public void gainExperience() {
        experience++;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - Experience: %d years", name, role, experience);
    }
}
