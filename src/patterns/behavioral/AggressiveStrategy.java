package patterns.behavioral;

import models.Car;
import exceptions.FuelException;
import exceptions.TireException;

// Агресивна стратегія - максимальна швидкість, високе споживання палива
public class AggressiveStrategy implements DrivingStrategy {
    
    @Override
    public void drive(Car car) throws FuelException, TireException {
        // Агресивне водіння - багато прискорень
        car.accelerate();
        car.accelerate();
        // Вища швидкість, але більше споживання палива та знос шин
    }
    
    @Override
    public String getStrategyName() {
        return "Aggressive";
    }
}
