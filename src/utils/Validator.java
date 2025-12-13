package utils;

// Клас для валідації даних - щоб не дублювати код перевірок
public class Validator {
    
    // Перевіряє що рядок не порожній
    public static String validateNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        return value.trim();
    }
    
    // Перевіряє що значення в допустимому діапазоні
    public static void validateRange(double value, double min, double max, String fieldName) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(
                String.format("%s must be between %.1f and %.1f", fieldName, min, max));
        }
    }
    
    // Перевіряє що значення не від'ємне
    public static void validateNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
    }
    
    // Перевіряє що int значення не від'ємне
    public static void validateNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
    }
    
    // Перевіряє що об'єкт не null
    public static void validateNotNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }
}

