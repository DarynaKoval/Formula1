package patterns.behavioral;

import models.Car;
import exceptions.FuelException;
import exceptions.TireException;

// Економна стратегія - економія палива, менше прискорень
public class EconomicalStrategy implements DrivingStrategy {
    
    @Override
    public void drive(Car car) throws FuelException, TireException {
        // Економне водіння - менше прискорень, більше гальмувань
        // Використовуємо перевірку щоб не витрачати багато палива
        if (car.getFuelLevel() > car.getMaxFuel() * 0.3) {
            car.accelerate();
        } else {
            // Якщо палива мало, їдемо повільніше
            car.brake();
        }
    }
    
    @Override
    public String getStrategyName() {
        return "Economical";
    }
}
