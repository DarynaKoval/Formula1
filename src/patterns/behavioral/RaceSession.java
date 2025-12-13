package patterns.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * RaceSession - сесія гонки яка використовує Observer pattern
 * 
 * Демонструє Observer pattern:
 * - RaceSession є Subject (суб'єкт)
 * - Має список observers (спостерігачів)
 * - Методи attach() та detach() для додавання/видалення спостерігачів
 * - Метод notifyObservers() для сповіщення всіх спостерігачів про події
 */
public class RaceSession {
    private List<RaceObserver> observers;
    private String sessionName;
    
    public RaceSession(String sessionName) {
        this.sessionName = sessionName;
        this.observers = new ArrayList<>();
    }
    
    // Додати спостерігача
    public void attach(RaceObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    // Видалити спостерігача
    public void detach(RaceObserver observer) {
        observers.remove(observer);
    }
    
    // Сповістити всіх спостерігачів про подію
    public void notifyObservers(String event) {
        for (RaceObserver observer : observers) {
            observer.update(event);
        }
    }
    
    // Метод для запуску гонки
    public void startRace() {
        notifyObservers("Гонка почалася!");
    }
    
    // Метод для завершення гонки
    public void finishRace() {
        notifyObservers("Гонка завершилася!");
    }
    
    // Метод для додавання події під час гонки
    public void addEvent(String event) {
        notifyObservers(sessionName + ": " + event);
    }
    
    public String getSessionName() {
        return sessionName;
    }
}
