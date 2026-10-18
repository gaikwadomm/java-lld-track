package multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

class EtaCalculator implements Callable<String> {
    @Override 
    public String call() throws InterruptedException {
            Thread.sleep(5000);
            System.out.println("Calculating ETA using the Callable");
            return "Eta : 25 Min";
    }
}

class SmsTask implements Runnable {
    @Override 
    public void run(){
        try{
            Thread.sleep(2000);
            System.out.println("SMS Sent Using Runnable");
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
} 

class EmailTask implements Runnable {
    @Override 
    public void run(){
        try {
            Thread.sleep(3000);
            System.out.println("Email Sent Using RUnnable");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ExecuteThread {
    public static void main(String args[]){
        ExecutorService threadExecutor = Executors.newFixedThreadPool(3);

        EtaCalculator etaThread = new EtaCalculator();
        SmsTask smsThread = new SmsTask();
        EmailTask emailThread = new EmailTask();

        Future<String> etaResult = threadExecutor.submit(etaThread);
        threadExecutor.submit(emailThread);
        threadExecutor.submit(smsThread);

        try{
            System.out.println(etaResult.get());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        threadExecutor.shutdown();
    }
}

// -- Using FutureTask --
// Implementing Callable to calculate ETA (only this task requires a return value)
class ETACalculationTask implements Callable<String> {
    public String call() throws InterruptedException {
        Thread.sleep(5000); // Simulate 5-second delay for ETA calculation
        System.out.println("ETA calculated using Callable.");
        return "ETA: 25 minutes"; // Return the ETA result
    }
}
 
// Implementing Runnable to send SMS (no return value required)
class SMSTask implements Runnable {
    public void run() {
        try {
            Thread.sleep(2000); // Simulate 2-second delay for SMS
            System.out.println("SMS Sent using Runnable.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
 
// Implementing Runnable to send Email (no return value required)
class EMAILTask implements Runnable {
    public void run() {
        try {
            Thread.sleep(3000); // Simulate 3-second delay for Email
            System.out.println("Email Sent using Runnable.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class UsingFutureTask {
    public static void main(String args[]){
        FutureTask<String> etaTask = new FutureTask<>(new ETACalculationTask());

 
        // Create Runnable tasks for SMS and Email
        SMSTask smsTask = new SMSTask();
        EMAILTask emailTask = new EMAILTask();
 
        // Create Thread objects to run all tasks
        Thread etaThread = new Thread(etaTask);
        Thread smsThread = new Thread(smsTask);
        Thread emailThread = new Thread(emailTask);
 
        System.out.println("Task Started.\n");
 
        // Start all threads
        etaThread.start();
        System.out.println("Task 1 ongoing...");
 
        smsThread.start();
        System.out.println("Task 2 ongoing...");
 
        emailThread.start();
        System.out.println("Task 3 ongoing...");

        try {
            // Get the result from the FutureTask for ETA
            System.out.println((String)etaTask.get()); // Wait for ETA task to finish and print result
            System.out.println(1);
            // Wait for SMS and Email tasks to finish (no result needed)
            smsThread.join();
            System.out.println(2);
            emailThread.join();
            System.out.println(3);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

public class ManagingThreads {
    
}
