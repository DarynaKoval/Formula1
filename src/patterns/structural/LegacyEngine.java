package patterns.structural;

// Старий двигун з іншим інтерфейсом (для Adapter)
public class LegacyEngine {
    private String model;
    private int horsepower; // в іншому форматі (int замість double)
    
    public LegacyEngine(String model, int horsepower) {
        this.model = model;
        this.horsepower = horsepower;
    }
    
    // Старий метод з іншою назвою
    public int getHorsepower() {
        return horsepower;
    }
    
    public String getModel() {
        return model;
    }
    
    // Старий метод
    public String getDescription() {
        return "Legacy " + model + " (" + horsepower + " HP)";
    }
}
