package multithreading.threadsafety;

class Worker {
    // volatile guarantees that the moment one thread changes this to false,
    // all other threads will immediately see the updated value.
    private volatile boolean running = true;

    public void doWork() {
        System.out.println("Worker thread started. Doing work...");
        
        // If 'running' wasn't volatile, this thread might cache it as 'true' 
        // and never see the main thread change it to 'false'.
        // It would get stuck in an infinite loop!
        long counter = 0;
        while (running) {
            counter++; // Simulating heavy background work
        }
        
        System.out.println("Worker thread noticed the flag change! Stopped gracefully.");
        System.out.println("Total loops executed: " + counter);
    }

    public void stopWork() {
        System.out.println("Main thread signaling worker to stop...");
        running = false; // Update the volatile flag
    }
}

public class VolatileKeyword {
    public static void main(String[] args) {
        Worker worker = new Worker();

        // Thread 1: Executes the long-running task
        Thread task1 = new Thread(() -> worker.doWork());
        task1.start();

        // Main Thread pauses for a brief moment to let task1 spin up 
        // and potentially cache the 'running' variable.
        try {
            Thread.sleep(100); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Thread 2 (Main Thread): Updates the flag
        worker.stopWork();

        try {
            task1.join(); // Wait for task1 to finish its execution
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread finished.");
    }
}