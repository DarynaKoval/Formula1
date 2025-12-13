package patterns.structural;

import static patterns.structural.StructuralConstants.DEFAULT_ERS_BOOST;

// Енергоефективна система - збільшує потужність (Decorator pattern)
public class EnergyRecoverySystem extends EngineDecorator {
    private double energyBoost;
    
    public EnergyRecoverySystem(CarComponent component, double energyBoost) {
        super(component);
        this.energyBoost = energyBoost;
    }
    
    @Override
    public double getPower() {
        return component.getPower() + energyBoost;
    }
    
    @Override
    public String getInfo() {
        return component.getInfo() + " + ERS (+" + energyBoost + " HP)";
    }
}

