package patterns.creational;

import models.Car;

/**
 * Builder - шаблон для побудови боліда
 * 
 * Демонструє шаблон Builder:
 * - Покрокова побудова складного об'єкта
 * - Гнучке налаштування параметрів
 * - Метод build() повертає готовий об'єкт
 * - Дозволяє не вказувати всі параметри одразу
 */
public class CarBuilder {
    
    private double enginePower;
    private double aerodynamics;
    private double maxFuel;
    private String tireType;
    private boolean isBuilt = false;
    
    // Конструктор з мінімальними параметрами
    public CarBuilder() {
        // Значення за замовчуванням
        this.enginePower = 700.0;
        this.aerodynamics = 7.0;
        this.maxFuel = 105.0;
        this.tireType = "Medium";
    }
    
    // Методи для встановлення параметрів (fluent interface)
    public CarBuilder setEnginePower(double enginePower) {
        this.enginePower = enginePower;
        return this; // повертаємо this для ланцюжкового виклику
    }
    
    public CarBuilder setAerodynamics(double aerodynamics) {
        this.aerodynamics = aerodynamics;
        return this;
    }
    
    public CarBuilder setMaxFuel(double maxFuel) {
        this.maxFuel = maxFuel;
        return this;
    }
    
    public CarBuilder setTireType(String tireType) {
        this.tireType = tireType;
        return this;
    }
    
    // Метод для побудови боліда з акцентом на потужність
    public CarBuilder focusOnPower() {
        this.enginePower = 900.0; // висока потужність
        this.aerodynamics = 7.5; // середня аеродинаміка
        return this;
    }
    
    // Метод для побудови боліда з акцентом на аеродинаміку
    public CarBuilder focusOnAerodynamics() {
        this.enginePower = 750.0; // середня потужність
        this.aerodynamics = 9.5; // висока аеродинаміка
        return this;
    }
    
    // Метод для побудови збалансованого боліда
    public CarBuilder buildBalanced() {
        this.enginePower = 800.0;
        this.aerodynamics = 8.5;
        this.maxFuel = 110.0;
        return this;
    }
    
    // Метод build() - створює готовий об'єкт
    public Car build() {
        if (isBuilt) {
            throw new IllegalStateException("Builder already used. Create a new Builder.");
        }
        
        Car car = new Car(enginePower, aerodynamics, maxFuel);
        car.setTireType(tireType);
        
        isBuilt = true;
        return car;
    }
}
