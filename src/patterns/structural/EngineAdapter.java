package patterns.structural;

// Adapter - адаптує старий двигун до нового інтерфейсу CarComponent
public class EngineAdapter implements CarComponent {
    private LegacyEngine legacyEngine;
    
    public EngineAdapter(LegacyEngine legacyEngine) {
        this.legacyEngine = legacyEngine;
    }
    
    @Override
    public String getComponentName() {
        return legacyEngine.getModel();
    }
    
    @Override
    public double getPower() {
        // Конвертуємо int в double
        return (double) legacyEngine.getHorsepower();
    }
    
    @Override
    public String getInfo() {
        // Адаптуємо старий метод до нового формату
        return legacyEngine.getDescription() + " (Adapted)";
    }
}
