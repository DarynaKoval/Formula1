package patterns.behavioral;

import models.Car;
import exceptions.FuelException;
import exceptions.TireException;

/**
 * Strategy - шаблон для різних стратегій водіння
 * 
 * Демонструє шаблон Strategy:
 * - Інтерфейс Strategy з методом drive()
 * - Різні реалізації стратегій (Aggressive, Economical, Balanced)
 * - Driver може використовувати різні стратегії під час гонки
 * - Легко додавати нові стратегії без зміни коду Driver
 */
public interface DrivingStrategy {
    
    // Метод для водіння з використанням стратегії
    void drive(Car car) throws FuelException, TireException;
    
    // Метод для отримання назви стратегії
    String getStrategyName();
}
