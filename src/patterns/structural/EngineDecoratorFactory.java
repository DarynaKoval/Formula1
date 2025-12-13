package patterns.structural;

import static patterns.structural.StructuralConstants.*;

// Фабричні методи для створення декораторів
public class EngineDecoratorFactory {
    public static CarComponent addTurbo(CarComponent engine) {
        return new TurboCharger(engine, DEFAULT_TURBO_BOOST);
    }
    
    public static CarComponent addERS(CarComponent engine) {
        return new EnergyRecoverySystem(engine, DEFAULT_ERS_BOOST);
    }
    
    public static CarComponent addBoth(CarComponent engine) {
        return new EnergyRecoverySystem(new TurboCharger(engine, DEFAULT_TURBO_BOOST), DEFAULT_ERS_BOOST);
    }
}

