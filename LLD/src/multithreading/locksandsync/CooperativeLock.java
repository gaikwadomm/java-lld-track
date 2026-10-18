package multithreading.locksandsync;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.InternalFrameFocusTraversalPolicy;

class CooperativeTimer {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final ReentrantLock lock = new ReentrantLock();

    public boolean tryLock(){
        return lock.tryLock();
    }

    public ScheduledFuture<?> callScheduler(Thread ownerThread, long miliSeconds){
        return scheduler.schedule(()->{
                System.out.println("Timed Out Reached - Intterupting the Owner");
                ownerThread.interrupt();
        }, miliSeconds, TimeUnit.MILLISECONDS);
    }

    public void unlockByOwner(){
        if(!lock.isHeldByCurrentThread()){
            throw new IllegalMonitorStateException(
                "Only the owing thread can unlock the lock!"
            );
        }   
        lock.unlock();

        System.out.println("Lock released by "+Thread.currentThread().getName());
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}

public class CooperativeLock {
    public static void main(String args[]){
        CooperativeTimer lockSeatF12 = new CooperativeTimer();

        Thread userA = new Thread(()->{
            if(!lockSeatF12.tryLock()){
                System.out.println("Idle User could not aquired the lock!");
                return;
            }

            ScheduledFuture<?> schedule = lockSeatF12.callScheduler(Thread.currentThread(), 3000);

            try{
                System.out.println(
                        "IdleUser acquired the lock and is now idle."
                );
                
                Thread.sleep(5000);

                System.out.println("UserA completed before the time out!");
            }
            catch(InterruptedException e){
                System.out.println("Timed Out request heat userA get Interrupted!");
                Thread.currentThread().interrupt();
            }
            finally{
                schedule.cancel(false);

                lockSeatF12.unlockByOwner();
            }

        }, "IdleUser");

        Thread userB = new Thread(()->{
            try{
                Thread.sleep(1000);

                while (!lockSeatF12.tryLock()) {
                    System.out.println(
                            "ActiveUser still waiting..."
                    );
                    Thread.sleep(1000);
                }

                try {
                    System.out.println(
                            "ActiveUser acquired the lock and booked!"
                    );
                } finally {
                    // Executed by ActiveUser, which owns the lock here.
                    lockSeatF12.unlockByOwner();
                }
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }, "ActiveUser");

        userA.start();
        userB.start();

        try{
            userA.join();
            userB.join();
            lockSeatF12.shutdown();
        } catch(Exception e){

        }
    }
}
