package patterns.structural;

// Decorator - базовий клас для декораторів двигуна
public abstract class EngineDecorator implements CarComponent {
    protected CarComponent component;
    
    public EngineDecorator(CarComponent component) {
        this.component = component;
    }
    
    @Override
    public String getComponentName() {
        return component.getComponentName();
    }
    
    @Override
    public double getPower() {
        return component.getPower();
    }
    
    @Override
    public String getInfo() {
        return component.getInfo();
    }
}
