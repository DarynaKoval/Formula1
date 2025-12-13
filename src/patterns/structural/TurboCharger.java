package patterns.structural;

import static patterns.structural.StructuralConstants.DEFAULT_TURBO_BOOST;

// Турбонаддув - додає потужність (Decorator pattern)
public class TurboCharger extends EngineDecorator {
    private double boost;
    
    public TurboCharger(CarComponent component, double boost) {
        super(component);
        this.boost = boost;
    }
    
    @Override
    public double getPower() {
        return component.getPower() + boost;
    }
    
    @Override
    public String getInfo() {
        return component.getInfo() + " + Turbo (+" + boost + " HP)";
    }
}

