package patterns.structural;


// PROBLEM OF CLASS EXPLOSION;
// When we try to use inheritance to add combination of behavior

// Each combination of pizza requires a new class
// class PlainPizza {}
// class CheesePizza extends PlainPizza {}
// class OlivePizza extends PlainPizza {}
// class StuffedPizza extends PlainPizza {}
// class CheeseStuffedPizza extends CheesePizza {}
// class CheeseOlivePizza extends CheesePizza {}
// class CheeseOliveStuffedPizza extends CheeseOlivePizza {}


// ==============================================================================
// 1. WHY AN INTERFACE? (The Component Contract)
// ==============================================================================
/**
 * WHY PIZZA IS AN INTERFACE:
 * 
 * In this design, a "Pizza" is purely a CONCEPT of something that has a description 
 * and a cost. It doesn't need to define any shared STATE (like a boolean 'isBaked') 
 * or common behavior yet. 
 * 
 * By making it an Interface, we establish a PURE CONTRACT. 
 * Any object in our system—whether it's a base pizza crust or a handful of olives—
 * can "act like a pizza" simply by fulfilling these two methods. This is crucial 
 * for the Decorator pattern because both the Base Pizzas and the Toppings need to 
 * be treated as the exact same type (Pizza) interchangeably.
 * 
 * WHY NOT AN ABSTRACT CLASS?
 * If we made Pizza an abstract class, every single topping would be forced into a 
 * strict inheritance tree. While fine here, what if later we want to decorate 
 * something else (like a Sandwich)? An interface keeps our contracts loosely coupled.
 */
interface Pizza {
    String getDescription();
    double getCost();
}

// ==============================================================================
// 2. CONCRETE COMPONENTS (Base Objects)
// ==============================================================================
// These are standard implementations. They are self-contained and don't rely 
// on other objects to calculate their cost or description.

class PlainPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Plain Pizza";
    }

    @Override
    public double getCost() {
        return 150.00;
    }
}

class MargheritaPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Margherita Pizza";
    }

    @Override
    public double getCost() {
        return 200.00;
    }
}

// ==============================================================================
// 3. WHY AN ABSTRACT CLASS? (The Decorator Base)
// ==============================================================================
/**
 * WHY PIZZADECORATOR IS AN ABSTRACT CLASS:
 * 
 * This is the heart of the architectural decision! We need an Abstract Class here 
 * because we need to manage STATE and enforce a CONSTRUCTOR.
 * 
 * 1. STATE: The decorator must hold a reference to the 'pizza' it is decorating. 
 *    (The 'protected Pizza pizza;' variable). Interfaces CANNOT hold instance variables.
 * 2. CONSTRUCTOR: We must force every child class (like ExtraCheese) to initialize 
 *    this variable via a constructor. Interfaces CANNOT have constructors.
 * 
 * If we didn't use an Abstract Class here, every single topping (ExtraCheese, Olives) 
 * would have to declare its own `Pizza pizza;` variable and write its own constructor. 
 * That violates the DRY (Don't Repeat Yourself) principle.
 * 
 * WHY IS IT MARKED 'ABSTRACT'?
 * We mark it abstract because it makes no sense to instantiate a generic "PizzaDecorator". 
 * A decorator must be a specific topping (like Olives).
 */
abstract class PizzaDecorator implements Pizza {
    // STATE: This is why we need an Abstract Class!
    protected Pizza pizza; 

    // CONSTRUCTOR: Forcing child classes to provide the state during instantiation.
    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
}   

// ==============================================================================
// 4. CONCRETE DECORATORS (The Wrappers)
// ==============================================================================
// Because PizzaDecorator is an Abstract Class, these classes inherit the 'pizza' 
// state variable for free, and just focus on adding their specific behavior.

class ExtraCheese extends PizzaDecorator {
    public ExtraCheese(Pizza pizza) {
        super(pizza); // Calls the Abstract Class constructor to set the state
    }

    @Override
    public String getDescription() {
        // Uses the state variable ('pizza') inherited from the Abstract Class
        return pizza.getDescription() + ", Extra Cheese"; 
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 40.0;
    }
}

class Olives extends PizzaDecorator {
    public Olives(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Olives";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 30.0;
    }
}

class StuffedCrust extends PizzaDecorator {
    public StuffedCrust(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Stuffed Crust";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 50.0;
    }
}

// ==============================================================================
// 5. MAIN EXECUTABLE (The Driver)
// ==============================================================================
public class Decorator {
    public static void main(String[] args) {
        
        System.out.println("--- Building the Pizza (Decorator Pattern) ---");
        
        // 1. Start with a basic Margherita Pizza (Concrete Component)
        Pizza myPizza = new MargheritaPizza();

        // 2. Wrap it with Extra Cheese
        // ExtraCheese 'is a' Pizza (via Interface), but 'has a' Pizza (via Abstract Class state)
        myPizza = new ExtraCheese(myPizza);

        // 3. Wrap that combined object with Olives
        myPizza = new Olives(myPizza);

        // 4. Wrap that combined object with Stuffed Crust
        myPizza = new StuffedCrust(myPizza);

        // Final Description and Cost
        System.out.println("Pizza Description: " + myPizza.getDescription());
        System.out.println("Total Cost: Rs" + myPizza.getCost());
    }
}