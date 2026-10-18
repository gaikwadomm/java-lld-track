package multithreading.threadsafety;

import java.util.concurrent.atomic.AtomicInteger;

class CountIncrementer {
    private AtomicInteger likes = new AtomicInteger(0);

    public void incrementLike(){
        int prev, curr;
        do{
            prev = likes.get();
            curr = prev + 1;
            // Working : Compare the prev with the current 
            // If user1 and user2 thread try to exectue at exact same time
            // user1 reads likes to be say 10 as well as user2 will also read it to be 10
            // Now on hardware level first user1 let say execute prev and curr
            // now curr is 11
            // before for user2 the prev was 
            // but now its 11 
            // so it will redo the above thing of prev = likes.get() and curr = prev+1;
            // (with the latest value to read)
            // in this way atomicity get maintained
        } while(!likes.compareAndSet(prev, curr));
    }

    public int getLikes(){
        return likes.get();
    }
}

public class AtomicKeyword {
    public static void main(String args[]){
        CountIncrementer cntIncrement = new CountIncrementer();

        Runnable counter = ()->{
            for(int i=0;i<1000;i++){
                cntIncrement.incrementLike();
            }
        };

        Thread task1 = new Thread(counter);
        Thread task2 = new Thread(counter);

        task1.start();
        task2.start();
        
        try{
            task1.join();
            task2.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Final Likes Calculated : "+cntIncrement.getLikes());
    }
}
