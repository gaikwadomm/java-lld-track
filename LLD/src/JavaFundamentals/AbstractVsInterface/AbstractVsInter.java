package JavaFundamentals.AbstractVsInterface;   

/**
 * INTERVIEW TOPIC: Abstract Class vs. Interface in System Design
 * SCENARIO: A Game Engine with living creatures and machines.
 */

// ==============================================================================
// 1. WHEN TO USE AN ABSTRACT CLASS (Identity, State, and Core Logic)
// ==============================================================================
/**
 * WHY ABSTRACT CLASS? 
 * We use it here because every game entity HAS mutable state (health, position) 
 * and REQUIRES a constructor to initialize that state. 
 * 
 * WHEN NOT TO USE AN INTERFACE?
 * If we made GameEntity an interface, we could not declare the 'health' variable 
 * (interfaces only allow public static final constants). Every child class would 
 * have to duplicate the 'health' variable and the takeDamage() logic.
 */
abstract class GameEntity {
    // State: Interfaces cannot have mutable instance variables.
    protected String name;
    protected int health;

    // Constructor: Interfaces cannot have constructors.
    public GameEntity(String name, int maxHealth) {
        this.name = name;
        this.health = maxHealth;
    }

    // Shared concrete logic: Modifies shared state.
    public void takeDamage(int amount) {
        this.health -= amount;
        System.out.println(name + " took " + amount + " damage. Health: " + this.health);
    }

    // Abstract contract: Forces children to define their unique spawn behavior.
    public abstract void spawn();
}

// ==============================================================================
// 2. WHEN TO USE AN INTERFACE (Capabilities and Multiple Inheritance)
// ==============================================================================
/**
 * WHY INTERFACE?
 * We use it here because "Flying" is a capability that spans completely 
 * unrelated families of objects (e.g., a biological Dragon and a mechanical Helicopter).
 * 
 * WHEN NOT TO USE AN ABSTRACT CLASS?
 * If Flyable was an abstract class, a Helicopter could not extend both 'Vehicle' 
 * and 'Flyable' because Java forbids multiple class inheritance. Interfaces bypass 
 * the inheritance bottleneck.
 */
interface Flyable {
    // Pure contract (Implicitly public and abstract)
    void takeOff();
    void fly(int altitude);
}

// Another capability to demonstrate multiple interface implementation
interface Attackable {
    void attack(GameEntity target);
}

// ==============================================================================
// 3. CONCRETE IMPLEMENTATIONS (Bringing it together)
// ==============================================================================

// A Dragon is a GameEntity (Identity), and it can Fly and Attack (Capabilities)
class Dragon extends GameEntity implements Flyable, Attackable {

    public Dragon(String name) {
        super(name, 500); // Utilizing the Abstract Class constructor
    }

    @Override
    public void spawn() {
        System.out.println("Dragon " + name + " hatches from an egg!");
    }

    @Override
    public void takeOff() {
        System.out.println(name + " flaps its giant wings and lifts off.");
    }

    @Override
    public void fly(int altitude) {
        System.out.println(name + " soars through the clouds at " + altitude + " feet.");
    }

    @Override
    public void attack(GameEntity target) {
        System.out.println(name + " breathes fire!");
        target.takeDamage(50);
    }
}

// A completely different base class tree to prove why Flyable MUST be an interface
abstract class Vehicle {
    protected int fuelLevel = 100;
    public abstract void turnOnEngine();
}

// Helicopter extends Vehicle, but can STILL be Flyable! 
// This is impossible with Abstract Classes.
class Helicopter extends Vehicle implements Flyable {
    
    @Override
    public void turnOnEngine() {
        System.out.println("Helicopter rotors spinning up. Fuel: " + fuelLevel);
    }

    @Override
    public void takeOff() {
        System.out.println("Helicopter lifts off vertically.");
    }

    @Override
    public void fly(int altitude) {
        System.out.println("Helicopter chopping through the air at " + altitude + " feet.");
    }
}

// ==============================================================================
// 4. MAIN EXECUTABLE (The Interview Demo)
// ==============================================================================
public class AbstractVsInter {
    public static void main(String[] args) {
        System.out.println("--- Abstract Class Demo (State & Identity) ---");
        Dragon smaug = new Dragon("Smaug");
        Dragon toothless = new Dragon("Toothless");
        
        smaug.spawn();
        // Smaug attacks Toothless (uses shared state management from Abstract Class)
        smaug.attack(toothless); 

        System.out.println("\n--- Interface Demo (Polymorphism & Capabilities) ---");
        Helicopter apache = new Helicopter();
        apache.turnOnEngine();

        // We can group completely unrelated objects by their Interface capability!
        // A Dragon and a Helicopter share no parent class, but both are Flyable.
        Flyable[] flyingThings = { smaug, apache };
        
        for (Flyable thing : flyingThings) {
            thing.takeOff();
            thing.fly(1000);
        }
    }
}