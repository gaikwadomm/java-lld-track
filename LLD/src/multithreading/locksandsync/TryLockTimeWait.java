package multithreading.locksandsync;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingTryLock {
    private int availableSeats = 1;
    private final ReentrantLock lock = new ReentrantLock();

    public void bookTicket(String user) {
        System.out.println(user + " is trying to book...");

        boolean lockAcquired = false;

        try {
            lockAcquired =
                    lock.tryLock(2, TimeUnit.SECONDS);

            if (!lockAcquired) {
                System.out.println(
                        user +
                        " could not acquire the lock. Try again later."
                );
                return;
            }

            System.out.println(user + " acquired the lock.");
            if (availableSeats > 0) {
                availableSeats--;
                System.out.println(
                    user + " successfully booked the ticket."
                );
            } else {
                System.out.println(
                    user + " could not book. No seats left."
                );
            }

            // Used only to demonstrate the timeout behavior.
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            System.out.println(
                    user + " was interrupted while waiting or working."
            );
        } finally {
            if (lockAcquired) {
                System.out.println(
                        user + " is releasing the lock."
                );
                lock.unlock();
            }
        }
    }
}

public class TryLockTimeWait {
    public static void main(String[] args)
            throws InterruptedException {

        TicketBookingTryLock booking =
                new TicketBookingTryLock();

        Thread user1 = new Thread(
                () -> booking.bookTicket("User 1")
        );

        Thread user2 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            booking.bookTicket("User 2");
        });

        user1.start();
        user2.start();

        user1.join();
        user2.join();
    }
}