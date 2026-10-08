package multithreading.using_runnable_interface;
import java.util.*;

class SmsTask implements Runnable {
    public void run(){
        try {
            Thread.sleep(2000); // 2-second delay for SMS
            System.out.println("SMS Sent using Runnable.");
        } catch (InterruptedException e) {
            e.printStackTrace();;
        }
    }
}

// Implementing the Runnable interface for sending Email
class EmailTask implements Runnable {
    public void run() {
        try {
            Thread.sleep(3000); // 3-second delay for Email
            System.out.println("Email Sent using Runnable.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Implementing the Runnable interface for calculating ETA
class ETATask implements Runnable {
    public void run() {
        try {
            Thread.sleep(5000); // 5-second delay for ETA calculation
            System.out.println("ETA Calculated using Runnable. Estimated Time: 25 minutes.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Runnable_interface {
    public static void main(String[] args) {
        SmsTask sms = new SmsTask();
        EmailTask email = new EmailTask();
        ETATask eta = new ETATask();

        Thread smsThread = new Thread(sms);
        Thread emailThread = new Thread(email);
        Thread etaThread = new Thread(eta);

        System.out.println("Task Started.\n");


        smsThread.start();
        System.out.println("Task 1 ongoing...");

        emailThread.start();
        System.out.println("Task 2 ongoing...");

        etaThread.start();
        System.out.println("Task 3 ongoing...");

        try {
            smsThread.join();
            emailThread.join();
            etaThread.join();
            System.out.println("All tasks completed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}