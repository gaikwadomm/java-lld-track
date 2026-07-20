package patterns.creational;
import java.util.*;;

class BurgerMeal {
    // Required 
    private final String Bun;
    private final String Patty;

    // Add Ons (Optional)
    private final boolean Cheese;
    private final boolean ExtraProtein;
    private final List<String> Veges;

    private BurgerMeal(BuildBurger builder){
        this.Bun = builder.Bun;
        this.Patty = builder.Patty;
        this.Cheese = builder.Cheese;
        this.ExtraProtein = builder.ExtraProtein;
        this.Veges = builder.Veges;
    }

    public void printDetails(){
        System.out.println("Bun: " + Bun);
        System.out.println("Patty: " + Patty);
        System.out.println("Cheese: " + (Cheese ? "Yes" : "No"));
        System.out.println("Extra Protein: " + (ExtraProtein ? "Yes" : "No"));
        System.out.println("Veges: " + (Veges == null ? "None" : Veges));
    }

    public static class BuildBurger {
        // Required 
        private final String Bun;
        private final String Patty;

        // Add Ons (Optional)
        private boolean Cheese;
        private boolean ExtraProtein;
        private List<String> Veges;

        BuildBurger(String Bun, String Patty){
            this.Bun = Bun;
            this.Patty = Patty;
        }

        // We written this bcoz while chaining the . next method should have the object of BuildBurger 
        // Or else it will give error 
        BuildBurger withCheese(boolean Cheese){
            this.Cheese = Cheese;
            return this;
        }

        BuildBurger withExtraProtein(boolean ExtraProtein){
            this.ExtraProtein = ExtraProtein;
            return this;
        }

        BuildBurger withVeges(List<String> Veges){
            this.Veges = Veges;
            return this;
        }
        

        BurgerMeal build(){
            return new BurgerMeal(this);
        }
    }
}

public class Builder {
    public static void main(String args[]){
        BurgerMeal meal1 = new BurgerMeal.BuildBurger("Multi Grain", "Corn")
                                .withCheese(true)
                                .withVeges(Arrays.asList("Lettuce", "Onion", "Tomato"))
                                .build();
        meal1.printDetails();
    }
}
