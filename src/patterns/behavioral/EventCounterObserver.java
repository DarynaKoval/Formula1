package patterns.behavioral;

// Спостерігач для підрахунку подій
public class EventCounterObserver implements RaceObserver {
    private String name;
    private int eventCount;
    
    public EventCounterObserver(String name) {
        this.name = name;
        this.eventCount = 0;
    }
    
    @Override
    public void update(String event) {
        eventCount++;
    }
    
    @Override
    public String getObserverName() {
        return name;
    }
    
    public int getEventCount() {
        return eventCount;
    }
}
