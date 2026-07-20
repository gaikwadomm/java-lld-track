package patterns.creational;


// Eager Loading (Thread Safe)
// Cons : Unecessary creation of even Not Required Objects as well
class JudgeAnaltyics {
    private static final JudgeAnaltyics judge = new JudgeAnaltyics();

    private JudgeAnaltyics() {
        System.out.println(">>> ALARM: The JudgeAnaltyics object was just created! <<<");
    }

    public static JudgeAnaltyics getInstance(){
        return judge;
    }

    public static void testMethod() {
        System.out.println("Even if I call this method judge obj will get created (As class is get Load). Which is fallback as it never required till now!");
    }
}

// Lazy Loading (Object creation on Execution Time)
// Not Thread Safe
class LazyJudge {
    private static LazyJudge judge;

    private LazyJudge(){

    }

    public static LazyJudge getInstance(){
        if(judge==null){
            LazyJudge.judge = new LazyJudge();
        }
        return judge;
    }
}

// Synchronized Keyword :
// This is the simplest way to ensure thread safety. 
// By synchronizing the method that creates the instance, 
// we can prevent multiple threads from creating separate instances at the same time. 
// However, this approach can lead to performance issues due to the overhead of synchronization.

// When you make a method synchronized, only one thread can execute it at a time. 
// To enforce this, the JVM has to do extra work under the hood: checking if the lock is available, 
// putting threads to sleep if it isn't, waking them up when it is, 
// and constantly switching context between threads. 
// All of this lock management takes CPU cycles and time—that is the overhead.

class SyncJudge {
    private static SyncJudge judge;

    private SyncJudge(){}

    public static synchronized SyncJudge getInstance(){
        if(judge==null){
            judge = new SyncJudge();
        }

        return judge;
    }
}

// Double Check Locking

// VOLATILE USE : 

// The Illusion of new Object()
// In Java code, creating an object looks like a single, atomic step. 
// But to the CPU and JVM, it actually happens in three distinct steps:

// Allocate memory: Set aside empty RAM space for the new DoubleCheck object.

// Initialize: Call the DoubleCheck() constructor and set up any variables inside it.

// Assign: Point the judge reference variable to that newly allocated memory space.

// The Danger: Instruction Reordering
// Modern compilers and CPUs are highly aggressive about optimizing performance. 
// If they think it will make the code run faster, they are allowed to reorder steps 2 and 3, 
// because in a single-threaded application, the end result is the same.

// However, in a multi-threaded application, reordering creates a fatal flaw. 
// Imagine the CPU reorders the steps to 1 ➔ 3 ➔ 2:

// Thread A enters the synchronized block.

// Thread A allocates memory (Step 1).

// Thread A assigns that empty memory space to judge (Step 3).

// Critical moment: judge is now not null, but the object inside is empty/half-baked because the constructor hasn't run yet.

// Thread B comes along and checks the first if(judge == null).

// Because Thread A already pointed judge to a memory address, Thread B sees that judge != null.

// Thread B completely skips the synchronized block and happily returns the judge object.

// Thread B tries to use the object, but since Thread A hasn't finished running the constructor (Step 2), 
// Thread B crashes the application with a NullPointerException or unpredictable behavior.


// Adding the volatile keyword applies a strict "memory barrier" to the judge variable.
// It prevents reordering: It explicitly forbids the JVM and CPU from reordering 
// the initialization (Step 2) and the assignment (Step 3). It guarantees 
// the constructor completely finishes running before the judge variable is updated.

// It guarantees visibility: It ensures that the moment Thread A finishes writing 
// the fully constructed object to judge, the updated value is instantly flushed to 
// main memory, ensuring Thread B sees the real, fully baked object.

class DoubleCheck {
    private static volatile DoubleCheck judge;

    private DoubleCheck(){

    }

    public static DoubleCheck getInstance(){
        if(judge==null){
            synchronized(DoubleCheck.class){
                if(judge==null){
                    judge = new DoubleCheck();
                }
            }
        }

        return judge;
    }
}


// Bill Pugh Singleton (Best Practice for Lazy Loading)
// This is a highly efficient way to implement the Singleton pattern. 
// It uses a static inner helper class to hold the Singleton instance. 
// The instance is created only when the inner class is loaded, 
// which happens only when getInstance() is called for the first time.



class BillPugh {
    private BillPugh() {
        System.out.println(">>> ALARM: The BillPugh object was just created! <<<");
    }

    private static class Holder {
        private static final BillPugh judge = new BillPugh();
    }

    public static BillPugh getInstance(){
        return Holder.judge;
    }

    public static void testMethod(){
        // This happens bcoz of how JVM works
        // It only load the class when it needed
        // Holder is not needed in testMethod 
        // So it dont load it 
        System.out.println("In this when I call this method, there is no issue as judge will not created unless and until its method for getInstance get called!");
    }
}




public class Singleton {
    public static void main(String[] args){
        // JudgeAnaltyics.testMethod();

        // JudgeAnaltyics judge1 = JudgeAnaltyics.getInstance();
        // JudgeAnaltyics judge2 = JudgeAnaltyics.getInstance();

        // System.out.println(judge1);
        // System.out.println(judge2);

        // LazyJudge judge1 = LazyJudge.getInstance();
        // LazyJudge judge2 = LazyJudge.getInstance();

        // System.out.println(judge1);
        // System.out.println(judge2);

        // SyncJudge judge1= SyncJudge.getInstance();
        // SyncJudge judge2 = SyncJudge.getInstance();

        // System.out.println(judge1);
        // System.out.println(judge2);

        // DoubleCheck judge1 = DoubleCheck.getInstance();
        // DoubleCheck judge2 = DoubleCheck.getInstance();

        // System.out.println(judge1);
        // System.out.println(judge2);

        // BillPugh judge1 = BillPugh.getInstance();
        // BillPugh judge2 = BillPugh.getInstance();

        // System.out.println(judge1);
        // System.out.println(judge2);

        BillPugh.testMethod();
    }
}