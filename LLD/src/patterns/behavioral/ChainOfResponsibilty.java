package patterns.behavioral;

abstract class SupportHandler {
    protected SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    abstract void handleReq(String handle);
}

class TechnicalSupport extends SupportHandler {
    @Override
    void handleReq(String handle){
        if(handle.equalsIgnoreCase("technical")){
            System.out.println("Technical Support being provided!");
        }
        else if(nextHandler != null){
            nextHandler.handleReq(handle);
        } else {
            System.out.println("Provided support handle not present!");
        }
    }
}

class BillingSupport extends SupportHandler {
    @Override
    void handleReq(String handle){
        if(handle.equalsIgnoreCase("billing")){
            System.out.println("Billing Support being provided!");
        }
        else if(nextHandler != null){
            nextHandler.handleReq(handle);
        } else {
            System.out.println("Provided support handle not present!");
        }
    }
}

class GeneralSupport extends SupportHandler {
    @Override
    void handleReq(String handle){
        if(handle.equalsIgnoreCase("general")){
            System.out.println("General Support being provided!");
        }
        else if(nextHandler != null){
            nextHandler.handleReq(handle);
        } else {
            System.out.println("Provided support handle not present!");
        }
    }
}

class DeliverySupport extends SupportHandler {
    @Override
    void handleReq(String handle){
        if(handle.equalsIgnoreCase("delivery")){
            System.out.println("Delivery Support being provided!");
        }
        else if(nextHandler != null){
            nextHandler.handleReq(handle);
        } else {
            System.out.println("Provided support handle not present!");
        }
    }
}

public class ChainOfResponsibilty {
    public static void main(String args[]){
        SupportHandler general = new GeneralSupport();
        SupportHandler billing = new BillingSupport();
        SupportHandler technical = new TechnicalSupport();
        SupportHandler delivery = new DeliverySupport();

        // Setting up the chain: general -> billing -> technical -> delivery
        general.setNextHandler(billing);
        billing.setNextHandler(technical);
        technical.setNextHandler(delivery);

        // Testing the chain of responsibility with different request types
        // general.handleReq("billing");
        // general.handleReq("delivery");
        general.handleReq("unknown");
    }
}