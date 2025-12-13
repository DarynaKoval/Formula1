package models;

import models.team.Driver;
import patterns.behavioral.RaceObserver;
import exceptions.FuelException;
import exceptions.TireException;
import java.util.*;
import static models.RaceConstants.*;

// Симуляція гонки - проводить гонку коло за колом та підраховує бали
public class Race {
    private String raceName;
    private int totalLaps;
    private List<RaceResult> results;
    private Map<Driver, Car> driverCars;
    private List<RaceObserver> observers;
    private boolean isRunning;
    
    public Race(String raceName, int totalLaps) {
        this.raceName = raceName;
        this.totalLaps = totalLaps > 0 ? totalLaps : DEFAULT_LAPS;
        this.results = new ArrayList<>();
        this.driverCars = new HashMap<>();
        this.observers = new ArrayList<>();
        this.isRunning = false;
    }
    
    public void addParticipant(Driver driver, Car car) {
        if (driver != null && car != null) {
            driverCars.put(driver, car);
            driver.setCar(car);
            results.add(new RaceResult(driver, results.size() + 1));
        }
    }
    
    public void addObserver(RaceObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    private void notifyObservers(String event) {
        for (RaceObserver observer : observers) {
            observer.update(event);
        }
    }
    
    public void startRace() {
        if (driverCars.isEmpty()) {
            throw new IllegalStateException("[RACE_ERROR] Cannot start race: No participants!");
        }
        
        isRunning = true;
        notifyObservers("Race started: " + raceName);
        
        // Симуляція кожного кола
        for (int lap = 1; lap <= totalLaps && isRunning; lap++) {
            simulateLap(lap);
            
            // Перевірка чи хтось фінішував
            if (checkRaceFinished()) {
                break;
            }
        }
        
        finishRace();
    }
    
    private void simulateLap(int lapNumber) {
        notifyObservers(String.format("Lap %d/%d", lapNumber, totalLaps));
        
        for (RaceResult result : results) {
            if (!result.isFinished()) {
                    Driver driver = result.getDriver();
                    Car car = driverCars.get(driver);
                    
                    if (car != null) {
                    double lapTime = calculateLapTime(car, driver);
                    if (result.getLapTime() == 0 || (lapTime > 0 && lapTime < result.getLapTime())) {
                        result.setLapTime(lapTime);
                    }
                    
                    // Оновлюємо стан боліда
                    try {
                        driver.drive(); // Використовує стратегію водіння
                        car.checkState();
                    } catch (FuelException e) {
                        notifyObservers("[WARNING] " + driver.getName() + ": " + e.getFullErrorInfo());
                    } catch (TireException e) {
                        notifyObservers("[WARNING] " + driver.getName() + ": " + e.getFullErrorInfo());
                    } catch (Exception e) {
                        notifyObservers("[WARNING] " + driver.getName() + " had a problem: " + e.getMessage());
                    }
                    
                    result.incrementLaps();
                }
            }
        }
        updatePositions();
    }
    
    // Розраховує час кола на основі боліда та гонщика
    private double calculateLapTime(Car car, Driver driver) {
        double baseTime = BASE_LAP_TIME - (car.getEnginePower() / ENGINE_POWER_DIVISOR) - 
                         (car.getAerodynamics() * AERODYNAMICS_MULTIPLIER);
        double skillBonus = driver.getSkillLevel() * SKILL_BONUS_MULTIPLIER;
        baseTime -= skillBonus;
        double randomFactor = RANDOM_FACTOR_MIN + (Math.random() * RANDOM_FACTOR_RANGE);
        baseTime *= randomFactor;
        
        return Math.max(MIN_LAP_TIME, baseTime);
    }
    
    // Сортує результати за позиціями
    private void updatePositions() {
        results.sort((r1, r2) -> {
            if (r1.getLapsCompleted() != r2.getLapsCompleted()) {
                return Integer.compare(r2.getLapsCompleted(), r1.getLapsCompleted());
            }
            if (r1.getLapTime() == 0) return 1;
            if (r2.getLapTime() == 0) return -1;
            return Double.compare(r1.getLapTime(), r2.getLapTime());
        });
        
        // Оновлюємо позиції
        for (int i = 0; i < results.size(); i++) {
            results.get(i).setPosition(i + 1);
        }
    }
    
    private boolean checkRaceFinished() {
        for (RaceResult result : results) {
            if (result.getLapsCompleted() >= totalLaps) {
                result.setFinished(true);
            }
        }
        
        // Якщо всі фінішували
        boolean allFinished = true;
        for (RaceResult result : results) {
            if (!result.isFinished()) {
                allFinished = false;
                break;
            }
        }
        
        return allFinished;
    }
    
    private void finishRace() {
        isRunning = false;
        
        for (RaceResult result : results) {
            result.setFinished(true);
        }
        
        ScoringSystem.sortByPosition(results);
        Map<Driver, Integer> points = ScoringSystem.calculatePoints(results);
        
        notifyObservers("Race finished! Calculating points...");
        
        StringBuilder raceReport = new StringBuilder();
        raceReport.append("\n=== Race Results ===\n");
        raceReport.append("Race: ").append(raceName).append("\n");
        raceReport.append("Total Laps: ").append(totalLaps).append("\n\n");
        
        for (RaceResult result : results) {
            raceReport.append(result.toString()).append("\n");
        }
        
        notifyObservers(raceReport.toString());
    }
    
    public List<RaceResult> getResults() {
        return new ArrayList<>(results);
    }
    
    public String getRaceName() {
        return raceName;
    }
    
    public void stopRace() {
        isRunning = false;
        notifyObservers("Race stopped!");
    }
    
    public boolean isRunning() {
        return isRunning;
    }
}

