package patterns.creational;

import models.team.TeamPrincipal;

/**
 * Singleton - шаблон для управління командою
 * 
 * Демонструє шаблон Singleton:
 * - Один екземпляр класу на всю програму
 * - Приватний конструктор
 * - Статичний метод getInstance() для отримання єдиного екземпляра
 * - Гарантує що є тільки один менеджер команди
 */
public class TeamManager {
    
    // Статична змінна для зберігання єдиного екземпляра
    private static TeamManager instance;
    
    private TeamPrincipal principal;
    
    // Приватний конструктор - не можна створити об'єкт ззовні
    private TeamManager() {
        this.principal = null;
    }
    
    // Статичний метод для отримання єдиного екземпляра
    public static TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }
    
    // Методи для роботи з командою
    
    public void setPrincipal(TeamPrincipal principal) {
        this.principal = principal;
    }
    
    public TeamPrincipal getPrincipal() {
        return principal;
    }
    
    // Метод для створення команди через TeamPrincipal
    public void createTeam(String principalName, int experience, double budget) {
        if (principal == null) {
            principal = new TeamPrincipal(principalName, experience, budget);
        }
        // Створення команди буде в класі Team
    }
    
    // Метод для отримання інформації про менеджера
    public String getInfo() {
        if (principal == null) {
            return "TeamManager: команда не створена";
        }
        return "TeamManager: " + principal.getName() + " керує командою";
    }
}
