package patterns.behavioral;

import models.Car;

// Критичний стан
public class CriticalState implements CarState {
    @Override
    public void handle(Car car) {
        // Критичний стан - потрібен негайний піт-стоп
        System.out.println("CRITICAL STATE! Need immediate pit-stop!");
    }
    
    @Override
    public String getStateName() {
        return "Critical";
    }
}

