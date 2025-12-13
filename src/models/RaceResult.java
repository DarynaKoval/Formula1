package models;

import models.team.Driver;

/**
 * Результат гонки для одного гонщика
 * Зберігає інформацію про позицію, час, бали
 */
public class RaceResult {
    private Driver driver;
    private int position; // місце (1, 2, 3, ...)
    private double lapTime; // час найшвидшого кола (секунди)
    private int points; // бали за гонку
    private boolean finished; // чи фінішував
    private int lapsCompleted; // кількість завершених кіл
    
    public RaceResult(Driver driver, int position) {
        this.driver = driver;
        this.position = position;
        this.lapTime = 0.0;
        this.points = 0;
        this.finished = false;
        this.lapsCompleted = 0;
    }
    
    // Геттери
    public Driver getDriver() {
        return driver;
    }
    
    public int getPosition() {
        return position;
    }
    
    public double getLapTime() {
        return lapTime;
    }
    
    public int getPoints() {
        return points;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    public int getLapsCompleted() {
        return lapsCompleted;
    }
    
    // Сеттери
    public void setPosition(int position) {
        this.position = position;
    }
    
    public void setLapTime(double lapTime) {
        if (lapTime > 0) {
            this.lapTime = lapTime;
        }
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
    public void incrementLaps() {
        this.lapsCompleted++;
    }
    
    public void setLapsCompleted(int laps) {
        if (laps >= 0) {
            this.lapsCompleted = laps;
        }
    }
    
    @Override
    public String toString() {
        return String.format("P%d. %s - %d points (Laps: %d, Best: %.2fs)", 
                position, driver.getName(), points, lapsCompleted, lapTime);
    }
}
