package patterns.behavioral;

import models.Car;

// Оптимальний стан
public class OptimalState implements CarState {
    @Override
    public void handle(Car car) {
        // Оптимальні умови - все працює ідеально
        System.out.println("Optimal conditions! Car works perfectly.");
    }
    
    @Override
    public String getStateName() {
        return "Optimal";
    }
}

