package models;

import models.team.Driver;
import exceptions.FuelException;
import exceptions.TireException;
import patterns.behavioral.*;
import utils.Validator;

// Болід Формули 1
// Всі поля приватні (інкапсуляція), доступ через методи
public class Car {
    
    private static final double MIN_ENGINE_POWER = 500.0;
    private static final double MAX_ENGINE_POWER = 1000.0;
    private static final double MIN_AERODYNAMICS = 1.0;
    private static final double MAX_AERODYNAMICS = 10.0;
    private static final double FUEL_CONSUMPTION_RATE = 2.5;
    
    private double enginePower;
    private double aerodynamics;
    private double fuelLevel;
    private double maxFuel;
    private String tireType;
    private Driver driver; // зв'язок з гонщиком
    private CarState state; // стан боліда (State pattern)
    
    public Car(double enginePower, double aerodynamics, double maxFuel) {
        setEnginePower(enginePower);
        setAerodynamics(aerodynamics);
        setMaxFuel(maxFuel);
        this.fuelLevel = maxFuel;
        this.tireType = "Medium";
        this.driver = null;
        this.state = new NormalState();
    }
    
    public double getEnginePower() {
        return enginePower;
    }
    
    public double getAerodynamics() {
        return aerodynamics;
    }
    
    public double getFuelLevel() {
        return fuelLevel;
    }
    
    public double getMaxFuel() {
        return maxFuel;
    }
    
    public String getTireType() {
        return tireType;
    }
    
    public Driver getDriver() {
        return driver;
    }
    
    public void setEnginePower(double enginePower) {
        Validator.validateRange(enginePower, MIN_ENGINE_POWER, MAX_ENGINE_POWER, "Engine power");
        this.enginePower = enginePower;
    }
    
    public void setAerodynamics(double aerodynamics) {
        Validator.validateRange(aerodynamics, MIN_AERODYNAMICS, MAX_AERODYNAMICS, "Aerodynamics");
        this.aerodynamics = aerodynamics;
    }
    
    public void setMaxFuel(double maxFuel) {
        if (maxFuel <= 0) {
            throw new IllegalArgumentException("Fuel tank capacity must be greater than 0");
        }
        this.maxFuel = maxFuel;
    }
    
    public void setFuelLevel(double fuelLevel) {
        Validator.validateRange(fuelLevel, 0, maxFuel, "Fuel level");
        this.fuelLevel = fuelLevel;
    }
    
    public void setTireType(String tireType) {
        if (tireType != null && (tireType.equals("Soft") || 
                                 tireType.equals("Medium") || 
                                 tireType.equals("Hard"))) {
            this.tireType = tireType;
        } else {
            throw new IllegalArgumentException("Tire type must be: Soft, Medium or Hard");
        }
    }
    
    public void setDriver(Driver driver) {
        // Якщо встановлюємо того самого водія, нічого не робимо
        if (this.driver == driver) {
            return;
        }
        
        // Якщо вже є інший водій, видаляємо зв'язок з ним
        if (this.driver != null) {
            Driver oldDriver = this.driver;
            this.driver = null; // Спочатку очищаємо, щоб уникнути рекурсії
            oldDriver.setCar(null);
        }
        
        this.driver = driver;
        
        // Встановлюємо двосторонній зв'язок
        if (driver != null && driver.getCar() != this) {
            driver.setCar(this);
        }
    }
    
    public CarState getState() {
        return state;
    }
    
    public void setState(CarState state) {
        this.state = state;
        if (state != null) {
            state.handle(this);
        }
    }
    
    // Визначає стан на основі палива та потужності
    public void checkState() {
        double fuelPercentage = (fuelLevel / maxFuel) * 100;
        if (fuelPercentage < 10 || enginePower > 950) {
            setState(new CriticalState());
        } else if (fuelPercentage < 30 || enginePower > 900) {
            setState(new OverheatingState());
        } else if (fuelPercentage > 70 && enginePower < 850 && aerodynamics > 8) {
            setState(new OptimalState());
        } else {
            setState(new NormalState());
        }
    }
    
    // Прискорення - споживає паливо
    public void accelerate() throws FuelException {
        if (fuelLevel <= 0) {
            throw new FuelException("No fuel! Cannot accelerate.", fuelLevel);
        }
        
        double consumption = FUEL_CONSUMPTION_RATE * (enginePower / MAX_ENGINE_POWER);
        consumeFuel(consumption);
        
        System.out.println("Car accelerated! Used " + String.format("%.2f", consumption) + " L fuel");
    }
    
    // Прискорення з перевіркою шин
    public void accelerateWithTireCheck() throws FuelException, TireException {
        if (fuelLevel <= 0) {
            throw new FuelException("No fuel! Cannot accelerate.", fuelLevel);
        }
        
        // М'які шини можуть перегрітися
        if (tireType.equals("Soft") && Math.random() < 0.1) {
            throw new TireException("Soft tires overheated!", tireType, "overheating");
        }
        
        double consumption = FUEL_CONSUMPTION_RATE * (enginePower / MAX_ENGINE_POWER);
        consumeFuel(consumption);
        
        System.out.println("Car accelerated! Used " + String.format("%.2f", consumption) + " L fuel");
    }
    
    public void brake() {
        System.out.println("Car braking...");
    }
    
    public void consumeFuel(double amount) {
        Validator.validateNonNegative(amount, "Fuel amount");
        
        fuelLevel -= amount;
        if (fuelLevel < 0) {
            fuelLevel = 0;
        }
    }
    
    public void refuel(double amount) {
        Validator.validateNonNegative(amount, "Fuel amount");
        
        fuelLevel += amount;
        if (fuelLevel > maxFuel) {
            fuelLevel = maxFuel;
        }
        
        System.out.println("Refueled " + String.format("%.2f", amount) + " L fuel. Current level: " + 
                          String.format("%.2f", fuelLevel) + " L");
    }
    
    // Виводить інформацію про стан боліда
    public String checkStatus() {
        StringBuilder status = new StringBuilder();
        status.append("=== Car Status ===\n");
        status.append("Engine Power: ").append(enginePower).append(" HP\n");
        status.append("Aerodynamics: ").append(aerodynamics).append("\n");
        status.append("Fuel: ").append(String.format("%.2f", fuelLevel))
              .append(" / ").append(String.format("%.2f", maxFuel)).append(" L\n");
        status.append("Tire Type: ").append(tireType).append("\n");
        
        double fuelPercentage = (fuelLevel / maxFuel) * 100;
        if (fuelPercentage < 20) {
            status.append("WARNING: Low fuel! (").append(String.format("%.1f", fuelPercentage)).append("%)\n");
        } else if (fuelPercentage > 80) {
            status.append("Tank almost full (").append(String.format("%.1f", fuelPercentage)).append("%)\n");
        }
        
        if (driver != null) {
            status.append("Driver: ").append(driver.getName()).append("\n");
        } else {
            status.append("Driver: not assigned\n");
        }
        
        return status.toString();
    }
    
    // Розраховує загальну продуктивність
    public double calculatePerformance() {
        double tireMultiplier = 1.0;
        if (tireType.equals("Soft")) {
            tireMultiplier = 1.2;
        } else if (tireType.equals("Hard")) {
            tireMultiplier = 0.9;
        }
        
        return (enginePower / 10.0) * aerodynamics * tireMultiplier;
    }
    
    @Override
    public String toString() {
        return String.format("Car: Engine=%.1f HP, Aero=%.1f, Fuel=%.1f/%.1f L, Tires=%s",
                enginePower, aerodynamics, fuelLevel, maxFuel, tireType);
    }
}
