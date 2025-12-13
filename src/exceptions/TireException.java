package exceptions;

// Виключення для проблем з шинами
// Виникає при проколі, перегріві або зносі шин
// Містить тип шин та тип проблеми
public class TireException extends F1Exception {
    
    private String tireType; // тип шин, з якими виникла проблема
    private String issueType; // тип проблеми (puncture, overheating, wear)
    
    public TireException(String message) {
        super(message, "TIRE_ERROR");
        this.tireType = "Unknown";
        this.issueType = "General";
    }
    
    public TireException(String message, String tireType, String issueType) {
        super(message, "TIRE_ERROR");
        this.tireType = tireType;
        this.issueType = issueType;
    }
    
    public String getTireType() {
        return tireType;
    }
    
    public String getIssueType() {
        return issueType;
    }
    
    @Override
    public String getFullErrorInfo() {
        return String.format("[TIRE_ERROR] %s (Tire type: %s, Issue: %s)", 
                getMessage(), tireType, issueType);
    }
}
