package exceptions;

// Базовий клас для всіх виключень, пов'язаних з Формулою 1
// Використовується як батьківський клас для інших виключень
// Дозволяє обробляти всі помилки F1 через один catch (F1Exception e)
public class F1Exception extends Exception {
    
    private String errorCode; // код помилки для ідентифікації
    
    // Конструктор з повідомленням
    public F1Exception(String message) {
        super(message);
        this.errorCode = "F1_ERROR";
    }
    
    // Конструктор з повідомленням та кодом помилки
    public F1Exception(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    // Геттер для коду помилки
    public String getErrorCode() {
        return errorCode;
    }
    
    // Метод для отримання повної інформації про помилку
    public String getFullErrorInfo() {
        return String.format("[%s] %s", errorCode, getMessage());
    }
}
