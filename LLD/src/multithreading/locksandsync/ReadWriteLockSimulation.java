package multithreading.locksandsync;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class StockData {
    private double price = 100.0;

    // Using the fully qualified name to avoid clashing with your custom class name below
    private final ReadWriteLock lock =
            new ReentrantReadWriteLock();

    public void updatePrice(double newPrice) {
        lock.writeLock().lock();

        try {
            System.out.printf(
                    "%s updating price to %.2f%n",
                    Thread.currentThread().getName(),
                    newPrice
            );

            // Simulating the time it takes to write to the database
            Thread.sleep(500); 
            price = newPrice;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public double readPrice() {
        lock.readLock().lock();

        try {
            System.out.printf(
                    "%s read price: %.2f%n",
                    Thread.currentThread().getName(),
                    price
            );
            
            // Simulating the time it takes to read from the database
            Thread.sleep(200); 
            return price;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        } finally {
            lock.readLock().unlock();
        }
    }
}

// Simulation Class
public class ReadWriteLockSimulation {
    public static void main(String[] args) {
        StockData stockData = new StockData();

        // Create 3 Reader Threads
        // These will execute concurrently because multiple threads can hold the Read lock at once.
        for (int i = 1; i <= 3; i++) {
            Thread reader = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    stockData.readPrice();
                    try {
                        Thread.sleep(100); // Wait a bit before reading again
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Reader-" + i);
            reader.start();
        }

        // Create 1 Writer Thread
        // When this thread wakes up and acquires the Write lock, all readers will be blocked.
        Thread writer = new Thread(() -> {
            for (int j = 0; j < 2; j++) {
                try {
                    Thread.sleep(300); // Wait before triggering an update
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                stockData.updatePrice(105.50 + j);
            }
        }, "Writer-1");
        writer.start();
    }
}


// Simulation with stoping read at write lock 
/*
import java.util.concurrent.locks.ReentrantReadWriteLock;

class StockData {
    private double price = 100.0;

    private final java.util.concurrent.locks.ReadWriteLock lock =
            new ReentrantReadWriteLock();

    // 1. A standard writer that takes a long time (hogs the lock)
    public void slowUpdatePrice(double newPrice) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " ACQUIRED Write Lock. Updating price...");
            
            // Hogging the lock for 3 seconds
            Thread.sleep(3000); 
            price = newPrice;
            
            System.out.println(Thread.currentThread().getName() + " FINISHED updating.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " RELEASED Write Lock.");
            lock.writeLock().unlock();
        }
    }

    // 2. A reader that gives up if the lock is already taken
    public void tryReadPrice() {
        System.out.println(Thread.currentThread().getName() + " is trying to read...");
        
        // tryLock() returns true if the door is open, false if it is locked
        if (lock.readLock().tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + " SUCCESS: Read price as " + price);
            } finally {
                lock.readLock().unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " FAILED: Not possible to read. The lock is already acquired by a Writer!");
        }
    }

    // 3. A writer that gives up if the lock is already taken
    public void tryUpdatePrice(double newPrice) {
        System.out.println(Thread.currentThread().getName() + " is trying to write...");
        
        if (lock.writeLock().tryLock()) {
            try {
                price = newPrice;
                System.out.println(Thread.currentThread().getName() + " SUCCESS: Updated price to " + price);
            } finally {
                lock.writeLock().unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " FAILED: Not possible to write. The lock is already acquired!");
        }
    }
}

// Simulation Class
public class ReadWriteLock {
    public static void main(String[] args) throws InterruptedException {
        StockData stockData = new StockData();

        // Actor 1: The selfish writer who holds the lock for a long time
        Thread selfishWriter = new Thread(() -> {
            stockData.slowUpdatePrice(150.0);
        }, "Selfish-Writer");

        // Actor 2: A reader who tries to read while the selfish writer is working
        Thread blockedReader = new Thread(() -> {
            stockData.tryReadPrice();
        }, "Blocked-Reader");

        // Actor 3: Another writer who tries to write while the selfish writer is working
        Thread blockedWriter = new Thread(() -> {
            stockData.tryUpdatePrice(200.0);
        }, "Blocked-Writer");

        // --- THE TIMELINE ---
        
        selfishWriter.start(); // Starts writing and locks the door for 3 seconds
        
        Thread.sleep(500); // Wait half a second to ensure Selfish-Writer is inside
        
        // Now, while Selfish-Writer is still inside, let's try to read and write
        blockedReader.start();
        blockedWriter.start();

        // Wait for everyone to finish before closing the program
        selfishWriter.join();
        blockedReader.join();
        blockedWriter.join();
    }
}
*/