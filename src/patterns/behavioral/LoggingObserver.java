package patterns.behavioral;

// Спостерігач для логування подій
public class LoggingObserver implements RaceObserver {
    private String name;
    private StringBuilder log;
    
    public LoggingObserver(String name) {
        this.name = name;
        this.log = new StringBuilder();
    }
    
    @Override
    public void update(String event) {
        log.append(event).append("\n");
    }
    
    @Override
    public String getObserverName() {
        return name;
    }
    
    public String getLog() {
        return log.toString();
    }
}
