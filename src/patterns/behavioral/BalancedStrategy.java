package patterns.behavioral;

import models.Car;
import exceptions.FuelException;
import exceptions.TireException;

// Збалансована стратегія - баланс між швидкістю та економією
public class BalancedStrategy implements DrivingStrategy {
    
    @Override
    public void drive(Car car) throws FuelException, TireException {
        // Збалансоване водіння - один раз прискорення
        car.accelerate();
        // Баланс між швидкістю та економією палива
    }
    
    @Override
    public String getStrategyName() {
        return "Balanced";
    }
}
