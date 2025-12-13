package patterns.behavioral;

import models.Car;

// Нормальний стан боліда
public class NormalState implements CarState {
    @Override
    public void handle(Car car) {
        // Нормальна робота, нічого особливого
    }
    
    @Override
    public String getStateName() {
        return "Normal";
    }
}
