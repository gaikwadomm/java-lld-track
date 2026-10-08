package multithreading.thread_run_method;

import java.util.*;
class SmsThread extends Thread {
    public void run(){
        try {
            Thread.sleep(3000);
            System.out.println("SMS Send Successfully!");
        } catch (InterruptedException e) {
            System.out.println("Exception : "+e.getMessage());
        }
    }
}

class EmailThread extends Thread {
    public void run(){
        try {
            Thread.sleep(2000);
            System.out.println("Email Send Successfully!");
        } catch (InterruptedException e) {
            System.out.println("Exception : "+e.getMessage());
        }
    }
}

class EtaCalcualation extends Thread {
    public void run(){
        try {
            Thread.sleep(5000);
            System.out.println("Eta Calculation Done Successfully!");
        } catch (InterruptedException e) {
            System.out.println("Exception : "+e.getMessage());
        }
    }
}

public class RunMultithreading {
    public static void main(String args[]){
        SmsThread sms = new SmsThread();
        EmailThread email = new EmailThread();
        EtaCalcualation eta = new EtaCalcualation();

        System.out.println("Starting Execuation of the Thread!");

        sms.start();
        System.out.println("Task 1 On Going");

        email.start();
        System.out.println("Task 2 On Going");

        eta.start();
        System.out.println("Task 3 On Going");

                // Wait for all threads to finish
        try {
            sms.join();
            email.join();
            eta.join();
            System.out.println("All tasks completed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
