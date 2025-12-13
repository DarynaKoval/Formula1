package exceptions;

// Виключення для проблем з паливом
// Виникає коли закінчилося паливо або неможливо прискорити
// Містить інформацію про поточний рівень палива
public class FuelException extends F1Exception {
    
    private double currentFuelLevel; // поточний рівень палива
    
    public FuelException(String message) {
        super(message, "FUEL_ERROR");
        this.currentFuelLevel = 0;
    }
    
    public FuelException(String message, double currentFuelLevel) {
        super(message, "FUEL_ERROR");
        this.currentFuelLevel = currentFuelLevel;
    }
    
    public double getCurrentFuelLevel() {
        return currentFuelLevel;
    }
    
    @Override
    public String getFullErrorInfo() {
        return String.format("[FUEL_ERROR] %s (Current fuel level: %.2f L)", 
                getMessage(), currentFuelLevel);
    }
}
