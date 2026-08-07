package patterns.structural;
import java.util.*;


interface Pizza {
    String getDescription();
    double getCost();
}

// ============= Concrete Components: Base pizza ==============
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


abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza){
        this.pizza = pizza;
    }
}

class OliveToppings extends PizzaDecorator{

    public OliveToppings(Pizza pizza){
        super(pizza);
    }

    @Override
    public String getDescription(){
        return super.pizza.getDescription() + " , Olive Toppings";
    }

    @Override
    public double getCost(){
        return super.pizza.getCost() + 30;
    }
}
class ExtraCheese extends PizzaDecorator{

    public ExtraCheese(Pizza pizza){
        super(pizza);
    }

    @Override
    public String getDescription(){
        return super.pizza.getDescription() + " , Extra Cheese";
    }

    @Override
    public double getCost(){
        return super.pizza.getCost() + 50;
    }
}
class StuffedCrust extends PizzaDecorator{

    public StuffedCrust(Pizza pizza){
        super(pizza);
    }

    @Override
    public String getDescription(){
        return super.pizza.getDescription() + " , Stuffed Crust";
    }

    @Override
    public double getCost(){
        return super.pizza.getCost() + 40;
    }
}


public class Decorator {
    public static void main(String[] args) {
        Pizza basePizza = new MargheritaPizza();

        basePizza = new OliveToppings(basePizza);
        basePizza = new StuffedCrust(basePizza);

        System.out.println(basePizza.getDescription());
        System.out.println(basePizza.getCost());
    }
}
