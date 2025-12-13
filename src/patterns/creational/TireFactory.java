package patterns.creational;

import models.Tire;

/**
 * Factory Method - шаблон для створення різних типів шин
 * 
 * Демонструє шаблон Factory Method:
 * - Один метод createTire() створює різні типи об'єктів
 * - Приховує деталі створення конкретних типів шин
 * - Легко додавати нові типи шин без зміни коду, що використовує фабрику
 */
public class TireFactory {
    
    // Метод фабрики - створює шини різних типів
    public static Tire createTire(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Tire type cannot be null");
        }
        
        type = type.trim();
        
        // Створюємо різні типи шин з різними характеристиками
        switch (type.toLowerCase()) {
            case "soft":
                // М'які шини - високе зчеплення, але низька довговічність
                return new Tire("Soft", 0.95, 0.5);
                
            case "medium":
                // Середні шини - баланс між зчепленням та довговічністю
                return new Tire("Medium", 0.80, 0.75);
                
            case "hard":
                // Жорсткі шини - низьке зчеплення, але висока довговічність
                return new Tire("Hard", 0.65, 0.95);
                
            default:
                throw new IllegalArgumentException("Unknown tire type: " + type + 
                    ". Use: Soft, Medium or Hard");
        }
    }
    
    // Метод для створення шин з параметрами
    public static Tire createCustomTire(String type, double grip, double durability) {
        Tire tire = createTire(type);
        // Можна додати додаткову логіку
        return tire;
    }
}
