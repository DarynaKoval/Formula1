package patterns.behavioral;

import models.Car;

// Стан перегріву
public class OverheatingState implements CarState {
    @Override
    public void handle(Car car) {
        // При перегріві потрібно зменшити навантаження
        System.out.println("WARNING: Car is overheating! Need to reduce load.");
    }
    
    @Override
    public String getStateName() {
        return "Overheating";
    }
}

