package multithreading.threadsafety;

class SafeCounter {
    private int count = 0;

    // Entire method is protected by the instance’s monitor lock
    public synchronized void increment() {
        count++;          // atomic under the lock
    }

    public synchronized int getCount() {
        return count;
    }
}

class SafeCounterSyncBlock {
    private int count = 0;
    
    public void increment(){
        synchronized(this){
            count++;
        }
    }

    public int getCount(){
        return count;
    }
}

public class HandledRaceCondition {
    public static void main(String args[]){
        SafeCounter purchSafeCounter = new SafeCounter();

        Runnable counter = ()->{
            for(int i=0;i<1000;i++){
                purchSafeCounter.increment();
            }
        };

        Thread task1 = new Thread(counter);
        Thread task2 = new Thread(counter);

        task1.start();
        task2.start();

        try{
            task1.join();
            task2.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Final Count : "+purchSafeCounter.getCount());
    }
}
