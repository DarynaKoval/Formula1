package models.team;

import models.Car;
import patterns.behavioral.DrivingStrategy;
import patterns.behavioral.BalancedStrategy;
import exceptions.FuelException;
import exceptions.TireException;

// Гонщик - наслідується від TeamMember
public class Driver extends TeamMember {

    private static final double MIN_SKILL_LEVEL = 1.0;
    private static final double MAX_SKILL_LEVEL = 10.0;
    private static final double TRAINING_INCREMENT = 0.1;

    private double skillLevel;
    private String country;
    private String teamName;
    private int wins;
    private Car car; // зв'язок з болідом
    private DrivingStrategy strategy; // стратегія водіння

    public Driver(String name, double skillLevel) {
        super(name, "Driver", 0);
        setSkillLevel(skillLevel);
        this.wins = 0;
        this.strategy = new BalancedStrategy();
    }

    // Конструктор з усіма параметрами
    public Driver(String name, double skillLevel, String country, String teamName, int experience) {
        super(name, "Driver", experience);
        setSkillLevel(skillLevel);
        this.country = country;
        this.teamName = teamName;
        this.wins = 0;
        this.strategy = new BalancedStrategy();
    }

    public double getSkillLevel() {
        return skillLevel;
    }

    public String getCountry() {
        return country;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getWins() {
        return wins;
    }
    
    public Car getCar() {
        return car;
    }
    
    public DrivingStrategy getStrategy() {
        return strategy;
    }

    public void setSkillLevel(double skillLevel) {
        if (skillLevel < MIN_SKILL_LEVEL || skillLevel > MAX_SKILL_LEVEL) {
            throw new IllegalArgumentException(
                String.format("Skill level must be between %.1f and %.1f", 
                    MIN_SKILL_LEVEL, MAX_SKILL_LEVEL));
        }
        this.skillLevel = skillLevel;
    }

    public void setCountry(String country) {
        this.country = (country != null) ? country.trim() : null;
    }

    public void setTeamName(String teamName) {
        this.teamName = (teamName != null) ? teamName.trim() : null;
    }
    
    public void setCar(Car car) {
        // Якщо встановлюємо ту саму машину, нічого не робимо
        if (this.car == car) {
            return;
        }
        
        // Якщо вже є інша машина, видаляємо зв'язок з нею
        if (this.car != null) {
            Car oldCar = this.car;
            this.car = null; // Спочатку очищаємо, щоб уникнути рекурсії
            oldCar.setDriver(null);
        }
        
        this.car = car;
        
        // Встановлюємо двосторонній зв'язок
        if (car != null && car.getDriver() != this) {
            car.setDriver(this);
        }
    }
    
    public void setStrategy(DrivingStrategy strategy) {
        if (strategy != null) {
            this.strategy = strategy;
        }
    }
    
    public void drive() {
        if (car != null && strategy != null) {
            try {
                strategy.drive(car);
            } catch (FuelException e) {
                System.out.println("[WARNING] " + getName() + ": " + e.getFullErrorInfo());
            } catch (TireException e) {
                System.out.println("[WARNING] " + getName() + ": " + e.getFullErrorInfo());
            } catch (Exception e) {
                System.out.println("[WARNING] " + getName() + " had a problem: " + e.getMessage());
            }
        }
    }

    // Реалізація work() - своя для гонщика (поліморфізм)
    @Override
    public void work() {
        System.out.println(name + " trains on track");
        train();
    }
    
    @Override
    public String getInfo() {
        return String.format("Driver: %s, Skills: %.1f, Country: %s, Team: %s, Wins: %d, Experience: %d years",
                name, skillLevel, 
                (country != null ? country : "N/A"),
                (teamName != null ? teamName : "N/A"),
                wins, experience);
    }
    
    // Тренування - підвищує навички
    public boolean train() {
        if (skillLevel >= MAX_SKILL_LEVEL) {
            return false;
        }
        
        skillLevel += TRAINING_INCREMENT;
        if (skillLevel > MAX_SKILL_LEVEL) {
            skillLevel = MAX_SKILL_LEVEL;
        }
        
        return true;
    }

    public void addWin() {
        wins++;
    }

    public void setWins(int wins) {
        if (wins < 0) {
            throw new IllegalArgumentException("Wins cannot be negative");
        }
        this.wins = wins;
    }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Driver)) {
            return false;
        }
        
        Driver driver = (Driver) obj;
        return name != null && name.equals(driver.name) &&
               Math.abs(skillLevel - driver.skillLevel) < 0.01;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int) skillLevel;
        return result;
    }
}
