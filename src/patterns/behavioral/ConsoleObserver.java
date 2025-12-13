package patterns.behavioral;

// Спостерігач для виводу подій в консоль
public class ConsoleObserver implements RaceObserver {
    private String name;
    
    public ConsoleObserver(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String event) {
        System.out.println("[" + name + "] Event: " + event);
    }
    
    @Override
    public String getObserverName() {
        return name;
    }
}
