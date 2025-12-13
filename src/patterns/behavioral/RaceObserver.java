package patterns.behavioral;

/**
 * Observer - шаблон для спостерігачів за гонкою
 * 
 * Демонструє шаблон Observer:
 * - Інтерфейс Observer з методом update()
 * - Суб'єкт (RaceSession) сповіщає спостерігачів про події
 * - Різні спостерігачі можуть реагувати на різні події
 * - Легко додавати нових спостерігачів
 */
public interface RaceObserver {
    
    // Метод який викликається коли відбувається подія
    void update(String event);
    
    // Метод для отримання назви спостерігача
    String getObserverName();
}
