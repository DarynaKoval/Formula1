package patterns.structural;

// Базовий інтерфейс для компонентів боліда (для Decorator)
public interface CarComponent {
    // Отримати назву компонента
    String getComponentName();
    
    // Отримати потужність компонента
    double getPower();
    
    // Отримати інформацію про компонент
    String getInfo();
}
