package multithreading.locksandsync;
import java.util.concurrent.Semaphore;

class TUFPlusAccount {
    private final Semaphore deviceSlot;

    public TUFPlusAccount(int maxDevice){
        this.deviceSlot = new Semaphore(maxDevice);
    }

    public boolean login(String user){
        System.out.println(user+" trying to login...");

        if(deviceSlot.tryAcquire()){
            System.out.println(user+" logged in successfully!");
            return true;
        }

        System.out.println(user + " denied login - too many devices.");
        return false;
    }

    public void logout(String user) {
        System.out.println(user + " logging out.");
        deviceSlot.release();
    }
}

public class SimaphoreSimulation {
    public static void main(String[] args)
            throws InterruptedException {

        TUFPlusAccount account =
                new TUFPlusAccount(2);

        Thread user1 = new Thread(
                () -> trySession(account, "User-A")
        );

        Thread user2 = new Thread(
                () -> trySession(account, "User-B")
        );

        Thread user3 = new Thread(
                () -> trySession(account, "User-C")
        );

        user1.start();
        user2.start();

        Thread.sleep(6000);
        user3.start();

        user1.join();
        user2.join();
        user3.join();
    }

    private static void trySession(
            TUFPlusAccount account,
            String user
    ) {
        if(!account.login(user)){
            return;
        }

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        finally{
            account.logout(user);
        }
    }
}