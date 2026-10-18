package multithreading.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPools {
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    
    public static void sendEmail(String recipient) {
        // 1. You can print a "submitted" message on the main thread
        System.out.println("Queued email for " + recipient);

        executor.execute(() -> {
            System.out.println("Preparing Email to " + recipient + " on " + Thread.currentThread().getName());

            try {
                Thread.sleep(5000); // Simulating time taken to send
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // 2. The "success" message MUST be inside the thread block
            System.out.println("SUCCESS: Email actually sent to " + recipient);
        });
    }   
    
    public static void main(String args[]) {
        for(int i = 0; i <= 25; i++) {
            sendEmail("user" + i + "@gmail.com");
        }

        // Tells the pool to stop accepting new tasks and shut down after current ones finish
        executor.shutdown(); 
    }
}