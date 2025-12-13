package patterns.behavioral;

import models.Car;

// State - шаблон для станів боліда
// Інтерфейс State з методом handle()
// Різні стани (Normal, Overheating, Optimal, Critical)
// Car може переходити між станами
public interface CarState {
    
    // Метод для обробки стану
    void handle(Car car);
    
    // Метод для отримання назви стану
    String getStateName();
}
