package models;

// Клас шини
public class Tire {
    
    private String type; // Soft, Medium, Hard
    private double grip; // зчеплення (0.0 - 1.0)
    private double durability; // довговічність (0.0 - 1.0)
    private double temperature; // температура (градуси)
    
    public Tire(String type, double grip, double durability) {
        this.type = type;
        this.grip = grip;
        this.durability = durability;
        this.temperature = 20.0; // початкова температура
    }
    
    // Геттери
    public String getType() {
        return type;
    }
    
    public double getGrip() {
        return grip;
    }
    
    public double getDurability() {
        return durability;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    // Сеттери
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    // Метод для зменшення довговічності після використання
    public void wear(double amount) {
        durability -= amount;
        if (durability < 0) {
            durability = 0;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Tire: %s, Grip: %.2f, Durability: %.2f, Temperature: %.1f°C",
                type, grip, durability, temperature);
    }
}
