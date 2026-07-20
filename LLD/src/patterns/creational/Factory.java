package patterns.creational;

interface PaymentGetWay {
    void makePayment();
}

class Gpay implements PaymentGetWay {
    @Override
    public void makePayment(){
        System.out.println("Welcome to Google Pay!");
    }
}
class PhonePay implements PaymentGetWay {
    @Override
    public void makePayment(){
        System.out.println("Welcome to Phone Pay!");
    }
}
class Paytm implements PaymentGetWay {
    @Override
    public void makePayment(){
        System.out.println("Welcome to Paytm!");
    }
}

class PaymentFactory {
    public static PaymentGetWay Payment(String payMethod){
        if(payMethod.equals("gpay")){
            return new Gpay();
        }
        else if(payMethod.equals("paytm")){
            return new Paytm();
        }
        else if(payMethod.equals("phonepay")){
            return new PhonePay();
        }
        return null;
    }
}

class Customer {
    private String payMode;
    Customer(String payMode){
        this.payMode = payMode;
    }

    void pay(){
        PaymentGetWay payMethod = PaymentFactory.Payment(this.payMode);
        if (payMethod != null) {
            payMethod.makePayment();
        }
    }
}

class Factory {
    public static void main(String args[]){
        Customer c1 = new Customer("gpay");
        c1.pay();
    }
}