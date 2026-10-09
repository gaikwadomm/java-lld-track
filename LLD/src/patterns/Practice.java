package patterns;

import java.lang.reflect.InvocationHandler;
import java.util.*;


//------------------------------------------------
// SINGLETON
//------------------------------------------------
class EagerLoading {
    private static final EagerLoading instance = new EagerLoading();

    private EagerLoading(){
        System.out.println("Eager Loading Get Instantiated ");
    }

    public static EagerLoading getInstace(){
        return instance;
    }
}

class LazyLoading{
    private static LazyLoading instance;

    private LazyLoading(){

    }

    public static LazyLoading getInstace(){
        if(instance==null){
            return instance = new LazyLoading();
        }
        return instance;
    }  
}

class DoubleChkSync{
    private static volatile DoubleChkSync instance;

    private DoubleChkSync(){

    }

    public  static DoubleChkSync getInstace(){
        if(instance==null){
            synchronized(DoubleChkSync.class){
                if(instance==null){
                    instance = new DoubleChkSync();
                }
            }
        }

        return instance;
    }
}

class BillPughSingleton{
    private  BillPughSingleton(){

    }

    private static class CreateInstance{
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance(){
        return CreateInstance.INSTANCE;
    }
}

//------------------------------------------------
// FACTORY
//------------------------------------------------

interface TravelService{
    void modeOfTravel();
}

class Air implements TravelService {
    @Override 
    public void modeOfTravel(){
        System.out.println("You are travelling by Air Mode");
    }
}

class Road implements TravelService{
    @Override 
    public  void modeOfTravel(){
        System.out.println("You are travelling by Road Mode");
    }
}

class TravelFactory {
    public static TravelService getTravelMode(String mode){
        if(mode.equalsIgnoreCase("AIR")){
            return new Air();
        }
        else if(mode.equalsIgnoreCase("ROAD")){
            return  new Road();
        }
        throw new IllegalArgumentException("Unknown Mode Provided : "+mode);
    }
}

//------------------------------------------------
// ABSTRACT FACTORY
//------------------------------------------------

interface PaymentGateway {
    void processPayment();
}
interface Invoice{
    void generateInvoice();
}

// India
class RazorPayGateway implements  PaymentGateway{
    @Override
    public void processPayment() {
        System.out.println("Razorpay Payment Processing");
    }
}
class PayUGateway implements  PaymentGateway{
    @Override
    public void processPayment() {
        System.out.println("PayU Payment Processing");
    }
}

class GSTInvoice implements Invoice{
    @Override
    public void generateInvoice() {
        System.out.println("GST Invoice Generated");
    }
}   

// Usa
class PayPal implements  PaymentGateway{
    @Override
    public void processPayment() {
       System.out.println("PayPal Payment Processing"); 
    }
}
class Stripe implements  PaymentGateway{
    @Override
    public void processPayment() {
        System.out.println("Stripe Payment Processing");
    }
}
class UsaInvoice implements Invoice{
    @Override
    public void generateInvoice() {
        System.out.println("USA Invoice Generated");
    }
}


interface RegionalFactory{
    PaymentGateway createPaymentGateway(String gatewayType);
    Invoice createInvoice();
}

// INDIAN FACTORY;
class IndiaFactory implements RegionalFactory {
    @Override
    public PaymentGateway createPaymentGateway(String getewayType) {
        if(getewayType.equalsIgnoreCase("razorpay")){
            return new RazorPayGateway();
        }
        else if(getewayType.equalsIgnoreCase("payu"));
        throw new IllegalArgumentException("Invalid Gate Way Type : "+getewayType);
    }

    @Override
    public Invoice createInvoice() {
        return  new GSTInvoice();
    }
}

class USFactory implements RegionalFactory {
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("paypal")) {
            return new PayPal();
        } else if (gatewayType.equalsIgnoreCase("stripe")) {
            return new Stripe();
        }
        throw new IllegalArgumentException("Unsupported gateway for US: " + gatewayType);
    }
 
    public Invoice createInvoice() {
        return new UsaInvoice();
    }
}


class CheckOutService{
    private PaymentGateway paymentGateway;
    private Invoice invoice;
    private String gatewayType;

    public CheckOutService(RegionalFactory factory, String gatewayType){
        this.gatewayType = gatewayType;
        this.paymentGateway = factory.createPaymentGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void completeOrder(double amount){
        paymentGateway.processPayment();
        invoice.generateInvoice();
    }
}

public  class Practice{
    public static void main(String args[]){
        // Scanner sc= new Scanner(System.in);

    }
}