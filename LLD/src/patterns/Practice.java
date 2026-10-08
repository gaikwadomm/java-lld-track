package patterns;

import java.util.*;

interface Item{
    // ItemVisitor is nothing but the 
    // Specific operation that needs to 
    // get applied base on the product / item type
    void accept(ItemVisitor visitor);
}

class PhysicalProduct implements Item{
    public String name;
    public double weight;

    public PhysicalProduct(String name, double weight){
        this.name = name;
        this.weight = weight;
    }

    @Override
    public void accept(ItemVisitor visitor){
        visitor.visit(this);
    }
}

class DigitalProduct implements Item {
    public String link;
    public double downloadSize;

    public DigitalProduct(String link, double size){
        this.link = link;
        this.downloadSize = size;
    }

    @Override
    public void accept(ItemVisitor visitor){
        visitor.visit(this);
    }
}

class GiftCard implements Item {
    public String couponCode;

    public GiftCard(String couponCode){
        this.couponCode = couponCode;
    }

    @Override
    public void accept(ItemVisitor visitor){
        visitor.visit(this);
    }
}

interface ItemVisitor{
    void visit(PhysicalProduct itemType);
    void visit(DigitalProduct itemType);
    void visit(GiftCard itemType);
} 

class InvoiceGenerator implements ItemVisitor{
    @Override
    public void visit(PhysicalProduct itemType){
        System.out.println("Physical Product Invoice : "+itemType.name+"!");
    }
    @Override
    public void visit(DigitalProduct itemType){
        System.out.println("Digital Product Invoice : Link "+itemType.link+"!");
    }
    @Override
    public void visit(GiftCard itemType){
        System.out.println("Gift Card Coupon Code : "+itemType.couponCode+"!");
    }
}
class ShippingCost implements ItemVisitor{
    @Override
    public void visit(PhysicalProduct itemType){
        System.out.println("Shipping Cost : "+(itemType.weight*10)+" for "+itemType.name+"!");
    }
    @Override
    public void visit(DigitalProduct itemType){
        System.out.println(itemType.link + " is digital -- No shipping cost.");
    }
    @Override
    public void visit(GiftCard itemType){
        System.out.println("GiftCard delivery via email -- No shipping cost.");
    }
}

public class Practice{
    public static void main(String args[]){
        List<Item> items = new ArrayList<>();
        items.add(new PhysicalProduct("Shoes", 1.2));
        items.add(new DigitalProduct("Ebook", 100));
        items.add(new GiftCard("TUF500"));

        ItemVisitor invoiceGenerator = new InvoiceGenerator();
        ItemVisitor shippingCalculator = new ShippingCost();

        for (Item item : items) {
            item.accept(invoiceGenerator);
            item.accept(shippingCalculator);
            
            System.out.println("");
        }
    }
}


