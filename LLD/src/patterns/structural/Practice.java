package patterns.structural;

interface PaymentGateway{
    void  pay(String orderId, double amount);
}

class Gpay implements PaymentGateway {
    @Override
    public void pay(String orderId, double amount){
        System.out.println("Done payment of "+amount+" using G-Pay for invoice: "+orderId);
    }
}

class Razorpay {
    public void makePayment(String invoiceId, double amountInRupees) {
        System.out.println("Paid Rs." + amountInRupees + " using Razorpay for invoice: " + invoiceId);
    }
}

class RazorpayAdapter implements PaymentGateway {
    private Razorpay razorPay;

    RazorpayAdapter(){
        this.razorPay = new Razorpay();
    }

    @Override
    public void pay(String orderId, double amount){
        this.razorPay.makePayment(orderId, amount);
    }
}

class CheckoutService {
    private PaymentGateway checkOutMethod;

    CheckoutService(PaymentGateway checkOutMethod){
        this.checkOutMethod = checkOutMethod;
    }

    public void checkout(String orderId, double amount){
        this.checkOutMethod.pay(orderId, amount);
    }
}

public class Practice {
    public static void main(String args[]){
        CheckoutService pay1 = new CheckoutService(new RazorpayAdapter());

        pay1.checkout("7", 7000);
    }    
}
