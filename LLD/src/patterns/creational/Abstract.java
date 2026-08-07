package patterns.creational;

interface PaymentGateWay{
    void processPayment(double amount);
}

interface Invoice{
    void generateInvoice();
}

// Indian Payment
class PhonePay implements PaymentGateWay {
    @Override
    public void processPayment(double amount){
        System.out.println("Payment of amount "+amount+" by Phone Pay!");
    }
}
class UPI implements PaymentGateWay {
    @Override
    public void processPayment(double amount){
        System.out.println("Payment of amount "+amount+"by UPI!");
    }
}

class GstInvoice implements Invoice {
    @Override
    public void generateInvoice(){
        System.out.println("GST Invoice generated!");
    }
}

// USA Payment
class Gpay implements PaymentGateWay {
    @Override
    public void processPayment(double amount){
        System.out.println("Payment of amount "+amount+" by G-Pay!");
    }
}
class RazorPay implements PaymentGateWay {
    @Override
    public void processPayment(double amount){
        System.out.println("Payment of amount "+amount+" by Razor Pay!");
    }
}

class UsaInvoice implements Invoice {
    @Override
    public void generateInvoice(){
        System.out.println("USA Invoice generated!");
    }
}
 

interface RegionFactory {
    PaymentGateWay createPaymentGateway(String gatewayType);
    Invoice createInvoice();
}

class IndianFactory implements RegionFactory {
    @Override
    public PaymentGateWay createPaymentGateway(String gateWayType){
        if(gateWayType.equalsIgnoreCase("upi")){
            return new UPI();
        }
        else if(gateWayType.equalsIgnoreCase("phonepay")){
            return new PhonePay();
        }
        throw new IllegalArgumentException("Unsupported gateway for India: " + gateWayType);
    }

    @Override
    public Invoice createInvoice(){
        return new GstInvoice();
    }
}

class UsaFactory implements RegionFactory {
    @Override
    public PaymentGateWay createPaymentGateway(String gateWayType){
        if(gateWayType.equalsIgnoreCase("gpay")){
            return new Gpay();
        }
        else if(gateWayType.equalsIgnoreCase("razorpay")){
            return new RazorPay();
        }
        throw new IllegalArgumentException("Unsupported gateway for India: " + gateWayType);
    }

    @Override
    public Invoice createInvoice(){
        return new UsaInvoice();
    }
}

// Checkout Service

class CheckoutService {
    private PaymentGateWay gateWay;
    private Invoice invoiceType;

    public CheckoutService(RegionFactory region, String checkoutMethod){
        this.gateWay = region.createPaymentGateway(checkoutMethod);
        this.invoiceType = region.createInvoice();
    }

    public void completeOrder(double amount){
        gateWay.processPayment(amount);
        invoiceType.generateInvoice();
    }
}



public class Abstract {
    public static void main(String args[]){
        UsaFactory obj1 = new UsaFactory();

        CheckoutService checkout = new CheckoutService(obj1, "razorpay");

        checkout.completeOrder(1000);
    }
}
