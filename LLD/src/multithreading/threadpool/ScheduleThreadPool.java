package multithreading.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class SessionCleaner {
    public static void main(String[] args){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = ()->System.out.println("System Session cleaning...");

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }
}

public class ScheduleThreadPool {
    public static void main(String args[]){
        SessionCleaner.main(args);
        
    }
}
