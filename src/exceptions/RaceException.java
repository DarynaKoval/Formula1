package exceptions;

// Виключення для загальних помилок під час гонки
// Використовується для аварій, технічних проблем та інших помилок
// Містить інформацію про фазу гонки (кваліфікація, гонка, піт-стоп)
public class RaceException extends F1Exception {
    
    private String racePhase; // фаза гонки (qualification, race, pit_stop)
    
    public RaceException(String message) {
        super(message, "RACE_ERROR");
        this.racePhase = "Unknown";
    }
    
    public RaceException(String message, String racePhase) {
        super(message, "RACE_ERROR");
        this.racePhase = racePhase;
    }
    
    public String getRacePhase() {
        return racePhase;
    }
    
    @Override
    public String getFullErrorInfo() {
        return String.format("[RACE_ERROR] %s (Race phase: %s)", 
                getMessage(), racePhase);
    }
}

