package patterns.structural;

// Базовий компонент - двигун
public class Engine implements CarComponent {
    private double power;
    private String name;
    
    public Engine(String name, double power) {
        this.name = name;
        this.power = power;
    }
    
    @Override
    public String getComponentName() {
        return name;
    }
    
    @Override
    public double getPower() {
        return power;
    }
    
    @Override
    public String getInfo() {
        return name + " Engine - Power: " + power + " HP";
    }
}
