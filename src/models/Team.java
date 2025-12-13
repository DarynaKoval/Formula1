package models;

import models.team.*;
import utils.Validator;
import java.util.ArrayList;
import java.util.List;

// Команда - управляє всією командою (GRASP: Information Expert, High Cohesion)
public class Team {
    private String teamName;
    private TeamPrincipal principal;
    private List<Driver> drivers;
    private List<Engineer> engineers;
    private List<Mechanic> mechanics;
    private Car car;
    
    public Team(String teamName, TeamPrincipal principal) {
        this.teamName = Validator.validateNonEmpty(teamName, "Team name");
        Validator.validateNotNull(principal, "Principal");
        this.principal = principal;
        this.drivers = new ArrayList<>();
        this.engineers = new ArrayList<>();
        this.mechanics = new ArrayList<>();
        this.car = null;
    }
    
    // Information Expert - Team знає про своїх членів
    public String getTeamName() {
        return teamName;
    }
    
    public TeamPrincipal getPrincipal() {
        return principal;
    }
    
    public List<Driver> getDrivers() {
        return new ArrayList<>(drivers); // Повертаємо копію для інкапсуляції
    }
    
    public List<Engineer> getEngineers() {
        return new ArrayList<>(engineers);
    }
    
    public List<Mechanic> getMechanics() {
        return new ArrayList<>(mechanics);
    }
    
    public Car getCar() {
        return car;
    }
    
    // Creator pattern - Team створює та додає Driver (через principal)
    public Driver addDriver(String name, double skillLevel, String country) {
        // Principal створює Driver (Creator pattern)
        Driver driver = principal.hireDriver(name, skillLevel, country);
        if (driver != null) {
            drivers.add(driver);
            if (car != null && drivers.size() == 1) {
                // Призначаємо першого гонщика до боліда
                driver.setCar(car);
            }
        }
        return driver;
    }
    
    // Додавання інженера
    public void addEngineer(Engineer engineer) {
        if (engineer != null && !engineers.contains(engineer)) {
            engineers.add(engineer);
        }
    }
    
    // Додавання механіка
    public void addMechanic(Mechanic mechanic) {
        if (mechanic != null && !mechanics.contains(mechanic)) {
            mechanics.add(mechanic);
        }
    }
    
    // Встановлення боліда команди
    public void setCar(Car car) {
        this.car = car;
        // Призначаємо першого гонщика до боліда якщо є
        if (car != null && !drivers.isEmpty()) {
            drivers.get(0).setCar(car);
        }
    }
    
    // High Cohesion - Team має методи для роботи з командою
    public int getTeamSize() {
        return 1 + drivers.size() + engineers.size() + mechanics.size(); // +1 для principal
    }
    
    // Отримати інформацію про команду
    public String getTeamInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== ").append(teamName).append(" ===\n");
        info.append("Principal: ").append(principal.getName()).append("\n");
        info.append("Drivers: ").append(drivers.size()).append("\n");
        info.append("Engineers: ").append(engineers.size()).append("\n");
        info.append("Mechanics: ").append(mechanics.size()).append("\n");
        info.append("Total team members: ").append(getTeamSize()).append("\n");
        if (car != null) {
            info.append("Car: Engine ").append(car.getEnginePower()).append(" HP\n");
        } else {
            info.append("Car: Not assigned\n");
        }
        return info.toString();
    }
    
    // Метод для отримання загального досвіду команди
    public int getTotalExperience() {
        int total = principal.getExperience();
        for (Driver driver : drivers) {
            total += driver.getExperience();
        }
        for (Engineer engineer : engineers) {
            total += engineer.getExperience();
        }
        for (Mechanic mechanic : mechanics) {
            total += mechanic.getExperience();
        }
        return total;
    }
}
