import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.time.*;
import java.math.*;

// ============================================================
// 16. PACKAGES AND IMPORTS
// ============================================================
// At the top of your file, include the necessary imports.
// Package syntax: package com.mycompany.lld; (usually at the very top)

// ============================================================
// 17. JAVA vs C++ IMPORTANT DIFFERENCES
// ============================================================
// C++ pointer                   -> Java reference
// nullptr                       -> null
// delete                        -> Not required (Garbage collection)
// destructor                    -> No deterministic destructor
// ->                            -> .
// struct                        -> class
// vector<int>                   -> List<Integer>
// string                        -> String
// pair                          -> record/custom class
// virtual                       -> Methods are virtual by default (unless final/private)
// multiple class inheritance    -> Not supported
// interface inheritance         -> Supported
// Primitive types are passed by value. Objects are passed by reference (the reference itself is passed by value).
// '==' compares object references (memory address). '.equals()' compares object values.
// 'final' is roughly equivalent to 'const' for variables, but prevents inheritance for classes and overriding for methods.
// 'static' belongs to the class, not the instance.

public class LLDJavaTemplate {

    // ============================================================
    // 1. BASIC JAVA SYNTAX
    // ============================================================
    public void basicSyntax() {
        // Primitive types
        int i = 10;
        long l = 100000L;
        double d = 10.5;
        float f = 10.5f;
        boolean b = true;
        char c = 'A';
        byte by = 1;
        short s = 2;

        // String and final
        String str = "Hello";
        final int CONSTANT_VAL = 100; // Cannot be modified

        // Type casting
        int castedInt = (int) d;
        double castedDouble = i;

        // if/else
        if (i > 5) { } else if (i == 5) { } else { }

        // switch (modern syntax available in newer Java, but standard shown)
        switch (i) {
            case 10: break;
            default: break;
        }

        // loops
        for (int j = 0; j < 5; j++) {
            if (j == 2) continue;
            if (j == 4) break;
        }

        int[] nums = {1, 2, 3};
        for (int num : nums) { // enhanced for loop
        }

        while (i > 0) { i--; }
        do { i++; } while (i < 10);

        // ternary
        int max = (i > 5) ? i : 5;
    }

    // ============================================================
    // 2. ARRAYS
    // ============================================================
    public void arraysDemo() {
        int[] arr = new int[5];
        int[] arr2 = {1, 2, 3, 4};

        arr[0] = 10;           // Update
        int val = arr[0];      // Access
        int len = arr.length;  // .length (property, not method)

        // Iteration
        for (int i = 0; i < arr2.length; i++) { }

        // 2D arrays
        int[][] grid = new int[3][4];
        int[][] grid2 = {{1, 2}, {3, 4}};

        // Arrays utility class
        Arrays.sort(arr2);
        Arrays.fill(arr, -1);
        int[] copy = Arrays.copyOf(arr2, arr2.length);
        String strArr = Arrays.toString(arr2); // Useful for printing
        boolean isEqual = Arrays.equals(arr, arr2);
        int index = Arrays.binarySearch(arr2, 3);
    }

    // ============================================================
    // 3. CLASSES AND OBJECTS
    // ============================================================
    static class User {
        // Fields (private for encapsulation)
        private final int id;
        private String name;
        private static int userCount = 0; // static field

        // Constructor
        public User(int id, String name) {
            this.id = id;
            this.name = name;
            userCount++;
        }

        // Constructor overloading
        public User(int id) {
            this(id, "Unknown"); // calling another constructor
        }

        // Getters and Setters
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        // Method overloading
        public void updateProfile(String name) { this.name = name; }
        public void updateProfile(String name, int dummy) { this.name = name; }

        // Method overriding
        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "'}";
        }
    }

    // ============================================================
    // 4. OOP FOR LLD
    // ============================================================
    // Abstraction & Interfaces
    interface PaymentStrategy {
        void pay(double amount); // public abstract by default
    }

    // Inheritance & implements
    static class CreditCardPayment implements PaymentStrategy {
        @Override
        public void pay(double amount) {
            System.out.println("Paid using Credit Card: " + amount);
        }
    }

    // Abstract classes
    abstract static class Vehicle {
        protected String brand; // Accessible to subclasses
        abstract void start();
        void stop() { System.out.println("Stopped"); }
    }

    // extends
    static class Car extends Vehicle {
        public Car(String brand) {
            this.brand = brand; // Accessing protected member
        }
        @Override
        void start() { System.out.println("Car started"); }
    }

    public void oopDemo() {
        // Upcasting (Interface reference pointing to implementation)
        PaymentStrategy payment = new CreditCardPayment();
        payment.pay(100.0);

        // Composition / Association is achieved by keeping references of other objects inside a class.
    }

    // ============================================================
    // 5. ENUMS
    // ============================================================
    // Basic enum
    enum VehicleType { CAR, BIKE, TRUCK }

    // Enum with fields/constructor/method
    enum Status {
        PENDING(1),
        COMPLETED(2);

        private final int code;

        Status(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
    }

    public void enumDemo() {
        Status s = Status.PENDING;
        // Enum with switch
        switch (s) {
            case PENDING: break;
            case COMPLETED: break;
        }
    }

    // ============================================================
    // 6. OBJECT CLASS METHODS
    // ============================================================
    static class CustomKey {
        private final int id;
        private final String name;

        public CustomKey(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // If a.equals(b) is true, then a.hashCode() MUST equal b.hashCode().
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CustomKey that = (CustomKey) o;
            return id == that.id && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "CustomKey{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }

    // ============================================================
    // 7. JAVA COLLECTIONS
    // ============================================================
    public void collectionsDemo() {
        // --- ArrayList ---
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.get(0);
        list.set(0, 20);
        list.remove(0);
        list.size();
        list.contains(20);
        list.isEmpty();
        list.clear();
        List<User> users = new ArrayList<>();

        // --- LinkedList ---
        // For most LLD use cases ArrayList is preferred due to cache locality,
        // unless linked-list behavior (O(1) middle insertion/deletion with iterators) is specifically needed.
        List<Integer> linkedList = new LinkedList<>();

        // --- HashMap ---
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 10);
        map.get("A");
        map.getOrDefault("B", 0);
        map.containsKey("A");
        map.remove("A");
        map.size();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        // Extremely useful for LLD (e.g., Adjacency List, Grouping)
        map.computeIfAbsent("C", k -> 100);

        // --- HashSet ---
        Set<Integer> set = new HashSet<>();
        set.add(10);
        set.remove(10);
        set.contains(10);
        set.size();
        for (int val : set) { }

        // --- TreeMap (Sorted Map based on Red-Black Tree) ---
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "A");
        treeMap.get(1);
        treeMap.remove(1);
        treeMap.containsKey(1);
        treeMap.firstKey();     // Smallest key
        treeMap.lastKey();      // Largest key
        treeMap.ceilingKey(2);  // Smallest key >= 2
        treeMap.floorKey(2);    // Largest key <= 2
        treeMap.higherKey(2);   // Smallest key > 2
        treeMap.lowerKey(2);    // Largest key < 2

        // --- TreeSet (Sorted Set) ---
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(10);
        treeSet.remove(10);
        treeSet.contains(10);
        treeSet.first();
        treeSet.last();
        treeSet.ceiling(10);
        treeSet.floor(10);
        treeSet.higher(10);
        treeSet.lower(10);

        // --- Queue ---
        // ArrayDeque is modern and preferred over LinkedList for standard queues
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(10); // inserts
        queue.poll();    // retrieves and removes head
        queue.peek();    // retrieves head without removing
        queue.isEmpty();

        // --- Deque ---
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(10);
        deque.addLast(20);
        deque.removeFirst();
        deque.removeLast();
        deque.peekFirst();
        deque.peekLast();

        // --- Stack ---
        // Prefer Deque over the legacy Stack class
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(10); // equivalent to addFirst
        stack.pop();    // equivalent to removeFirst
        stack.peek();   // equivalent to peekFirst

        // --- PriorityQueue (Heap) ---
        // Min heap (default)
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // Max heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        // Custom objects
        PriorityQueue<CustomKey> customPq = new PriorityQueue<>((a, b) -> Integer.compare(a.id, b.id));
    }

    // ============================================================
    // 8. C++ STL → JAVA MAPPING
    // ============================================================
    // C++ vector             -> Java ArrayList
    // C++ unordered_map      -> Java HashMap
    // C++ map                -> Java TreeMap
    // C++ unordered_set      -> Java HashSet
    // C++ set                -> Java TreeSet
    // C++ queue              -> Java Queue / ArrayDeque
    // C++ stack              -> Java Deque
    // C++ priority_queue     -> Java PriorityQueue
    // C++ pair               -> Java record / custom class

    // push_back()            -> add()
    // vector[i]              -> get(i)
    // vector.size()          -> size()
    // map[key]               -> get(key)
    // map[key] = value       -> put(key, value)
    // insert()               -> add()
    // pq.push()              -> offer()
    // pq.pop()               -> poll()
    // pq.top()               -> peek()

    // ============================================================
    // 9. GENERICS
    // ============================================================
    static class Box<T> {
        private T value;
        public Box(T value) { this.value = value; }
        public T getValue() { return value; }
    }

    public <K, V> void genericMethod(K key, V value) {
        // Generic method syntax
    }

    public void genericsDemo() {
        List<String> strings = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        Set<User> users = new HashSet<>();
        Queue<Runnable> tasks = new ArrayDeque<>();

        // Wildcards: level useful for LLD
        List<? extends Vehicle> cars = new ArrayList<Car>(); // upper bound (read-only mostly)
        List<? super Car> vehicles = new ArrayList<Vehicle>(); // lower bound (can add Cars)
    }

    // ============================================================
    // 10. STRING AND STRINGBUILDER
    // ============================================================
    public void stringsDemo() {
        String s = "Hello World";
        s.length();
        s.charAt(0);
        s.substring(0, 5); // "Hello"
        s.equalsIgnoreCase("hello world");
        s.contains("World");
        s.startsWith("He");
        s.endsWith("ld");
        String[] parts = s.split(" ");
        s.isEmpty();
        s.isBlank(); // true if empty or only whitespace
        char[] chars = s.toCharArray();
        s.toLowerCase();
        s.toUpperCase();
        s.trim();

        // String value comparison:
        // USE equals()
        // NOT ==
        String a = "test";
        String b = new String("test");
        a.equals(b); // true
        // a == b would be false

        // StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" World");
        sb.insert(0, "Say ");
        sb.deleteCharAt(0);
        sb.reverse();
        String result = sb.toString();
    }

    // ============================================================
    // 11. COMPARATOR AND SORTING
    // ============================================================
    static class Item { int price; String name; }

    public void sortingDemo() {
        List<Integer> list = new ArrayList<>();
        // Java 8 lambda syntax
        list.sort((a, b) -> Integer.compare(a, b));

        List<Item> items = new ArrayList<>();
        // Custom object sorting
        items.sort((a, b) -> Integer.compare(a.price, b.price));

        // Using Comparator utility
        items.sort(Comparator.comparingInt(item -> item.price));
        items.sort(Comparator.comparing((Item item) -> item.name)
                             .thenComparingInt(item -> item.price)
                             .reversed());
    }
    // C++:
    // sort(v.begin(), v.end(), [](auto &a, auto &b) {
    //     return a.price < b.price;
    // });
    //
    // Java:
    // list.sort((a, b) -> Integer.compare(a.price, b.price));

    // ============================================================
    // 12. RECORD / PAIR EQUIVALENT
    // ============================================================
    // Records (Java 14+) provide immutable data carriers with auto-generated equals/hashCode/toString
    // Useful for simple composite keys or DTOs
    record Pair(int first, int second) {}

    // Normal custom class (because LLD often needs behavior or mutability)
    static class CustomPair {
        int first;
        int second;
        CustomPair(int first, int second) {
            this.first = first;
            this.second = second;
        }
        // Usually add equals(), hashCode(), etc.
    }

    // ============================================================
    // 13. COMMON JAVA UTILITIES USED IN LLD
    // ============================================================
    public void utilitiesDemo() {
        // Objects
        boolean isEq = Objects.equals("A", "B");
        String nonNullString = Objects.requireNonNull("Value", "Value cannot be null");

        // Collections
        List<Integer> list = new ArrayList<>();
        Collections.sort(list);
        Collections.reverse(list);
        Collections.max(list);
        Collections.min(list);
        Collections.frequency(list, 10);

        // UUID
        String id = UUID.randomUUID().toString();

        // Date/time
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(5);
        Instant instant = Instant.now();

        // BigDecimal (Crucial for money/currency handling in LLD)
        BigDecimal amount = new BigDecimal("100.50");
        BigDecimal total = amount.add(new BigDecimal("50.25"));
    }

    // ============================================================
    // 14. OPTIONAL
    // ============================================================
    public void optionalDemo() {
        String value = "Hello";
        Optional<String> name = Optional.ofNullable(value); // use Optional.of() if strictly non-null
        boolean present = name.isPresent();
        String result = name.orElse("Default");
        String resultLazy = name.orElseGet(() -> "Computed Default");
        name.ifPresent(val -> System.out.println(val));
    }

    // ============================================================
    // 15. EXCEPTION HANDLING
    // ============================================================
    static class InvalidPaymentException extends RuntimeException {
        public InvalidPaymentException(String message) {
            super(message);
        }
    }

    public void exceptionDemo() {
        try {
            if (true) throw new InvalidPaymentException("Failed");
        } catch (InvalidPaymentException e) {
            // handle specific
        } catch (Exception e) {
            // handle broad
        } finally {
            // cleanup resources
        }
    }

    void testChecked() throws Exception {
        throw new Exception("Checked exception requires throws declaration");
    }

    // ============================================================
    // MULTITHREADING / CONCURRENCY
    // ============================================================

    // ============================================================
    // 18. THREAD
    // ============================================================
    static class MyThread extends Thread {
        @Override
        public void run() { System.out.println("Running MyThread"); }
    }

    public void threadDemo() throws InterruptedException {
        Runnable task = () -> {
            System.out.println("Running task");
        };

        Thread thread = new Thread(task);
        thread.start(); // start() creates/runs a new thread.
        // Calling run() directly does NOT create a new thread.
        
        thread.join(); // wait for thread to finish
        // thread.sleep(1000);
        // thread.interrupt();
        // thread.isAlive();
    }

    // ============================================================
    // 19 & 20. SYNCHRONIZED & RACE CONDITION
    // ============================================================
    // Unsafe counter (Race condition exists)
    static class Counter {
        int count = 0;
        void increment() { count++; }
    }

    // Safe counter
    static class SafeCounter {
        private int count = 0;
        private final Object lock = new Object();

        // synchronized method (lock is the instance 'this')
        public synchronized void increment() {
            count++;
        }

        // synchronized block
        public void decrement() {
            synchronized (lock) { // explicitly specifying the lock object
                count--;
            }
        }
    }

    // ============================================================
    // 21. VOLATILE
    // ============================================================
    static class VolatileDemo {
        private volatile boolean running = true;
        // volatile provides visibility across threads,
        // but does NOT make compound operations like count++ atomic.
        
        void stop() { running = false; }
        void work() { while (running) { /* do work */ } }
    }

    // ============================================================
    // 22. ATOMIC VARIABLES
    // ============================================================
    public void atomicsDemo() {
        AtomicInteger counter = new AtomicInteger(0);
        counter.incrementAndGet();
        counter.decrementAndGet();
        counter.get();
        counter.set(10);
        counter.compareAndSet(10, 20); // CAS operation

        AtomicLong l = new AtomicLong(0);
        AtomicBoolean b = new AtomicBoolean(false);
    }

    // ============================================================
    // 23. LOCK / REENTRANTLOCK
    // ============================================================
    public void reentrantLockDemo() {
        Lock lock = new ReentrantLock();
        lock.lock();
        // lock.tryLock() can be used to avoid blocking forever
        try {
            // critical section
        } finally {
            // finally is CRUCIAL so lock is released even if exception occurs
            lock.unlock();
        }
    }

    // ============================================================
    // 24. READWRITELOCK
    // ============================================================
    public void readWriteLockDemo() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        // Read Lock (multiple threads can read simultaneously)
        lock.readLock().lock();
        try {
            // read state
        } finally {
            lock.readLock().unlock();
        }

        // Write Lock (exclusive)
        lock.writeLock().lock();
        try {
            // mutate state
        } finally {
            lock.writeLock().unlock();
        }
    }

    // ============================================================
    // 25 & 26. CONDITION & WAIT/NOTIFY
    // ============================================================
    static class ConditionDemo {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        boolean ready = false;

        void awaitCondition() throws InterruptedException {
            lock.lock();
            try {
                while (!ready) condition.await(); // releases lock and waits
            } finally { lock.unlock(); }
        }

        void signalCondition() {
            lock.lock();
            try {
                ready = true;
                condition.signalAll(); // or condition.signal()
            } finally { lock.unlock(); }
        }
    }

    static class WaitNotifyDemo {
        private final Object lock = new Object();
        boolean ready = false;
        
        // wait()/notify()/notifyAll()
        // must be called while holding the object's monitor.
        void doWait() throws InterruptedException {
            synchronized (lock) {
                while (!ready) lock.wait(); 
            }
        }
        void doNotify() {
            synchronized (lock) {
                ready = true;
                lock.notifyAll(); // prefer notifyAll to prevent missed signals
            }
        }
    }

    // ============================================================
    // 27 & 28. EXECUTOR SERVICE, CALLABLE, FUTURE
    // ============================================================
    public void executorDemo() throws Exception {
        // newFixedThreadPool, newCachedThreadPool, newSingleThreadExecutor
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // submit Runnable
        executor.submit(() -> { System.out.println("Task"); });

        // Callable & Future
        Callable<Integer> task = () -> 10;
        Future<Integer> future = executor.submit(task);
        
        // Blocking wait for result
        Integer result = future.get();
        // future.isDone(); future.cancel(true);

        executor.shutdown(); // initiate graceful shutdown
        // executor.shutdownNow(); // force shutdown
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    // ============================================================
    // 29. COMPLETABLEFUTURE
    // ============================================================
    public void completableFutureDemo() {
        CompletableFuture
            .supplyAsync(() -> "Data")
            .thenApply(data -> data + " Processed")
            .thenAccept(result -> System.out.println(result))
            .exceptionally(ex -> {
                System.out.println("Error: " + ex);
                return null;
            });
            
        // Others: runAsync, thenCompose, thenCombine, allOf
    }

    // ============================================================
    // 30. SYNCHRONIZATION UTILITIES
    // ============================================================
    public void syncUtilitiesDemo() throws InterruptedException, BrokenBarrierException {
        // CountDownLatch: Wait until a fixed number of operations complete (LLD: fan-out/fan-in)
        CountDownLatch latch = new CountDownLatch(3);
        latch.countDown();
        latch.await();

        // CyclicBarrier: Threads wait for each other at a common barrier point
        CyclicBarrier barrier = new CyclicBarrier(3);
        barrier.await();

        // Semaphore: Control access to a shared resource with limited capacity (LLD: connection pools)
        Semaphore semaphore = new Semaphore(3);
        semaphore.acquire();
        try {
            // limited resource access
        } finally {
            semaphore.release();
        }
    }

    // ============================================================
    // 31. CONCURRENT COLLECTIONS
    // ============================================================
    public void concurrentCollectionsDemo() throws InterruptedException {
        // Preferred for concurrent key-value state
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 10);
        map.computeIfAbsent("B", k -> 20);

        // Good when reads vastly outnumber writes (LLD: Event listeners)
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        // Thread-safe queue blocking on capacity (LLD: Producer-Consumer pattern)
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.put(10); // blocks if full
        queue.take();  // blocks if empty

        // Non-blocking concurrent queues
        ConcurrentLinkedQueue<Integer> clq = new ConcurrentLinkedQueue<>();
    }

    // ============================================================
    // 32. THREAD-SAFE SINGLETON
    // ============================================================
    // Modern preferred approach (Enum handles thread-safety and serialization automatically)
    enum EnumSingleton {
        INSTANCE;
        public void doSomething() { }
    }

    // Double-checked locking approach (classic)
    static class DclSingleton {
        private static volatile DclSingleton instance;
        private DclSingleton() {}
        
        public static DclSingleton getInstance() {
            if (instance == null) {
                synchronized (DclSingleton.class) {
                    if (instance == null) {
                        instance = new DclSingleton();
                    }
                }
            }
            return instance;
        }
    }

    // ============================================================
    // 33 & 34. DEADLOCK, RACE CONDITION, CONCEPTS
    // ============================================================
    // Race Condition: Multiple threads modifying shared data concurrently without sync.
    // Deadlock: Two threads wait for locks held by each other.
    // Starvation: A thread never gets CPU time/lock because others monopolize it.
    // Thread Safety: Code functions correctly during concurrent execution.
    // Atomicity: Operation completes fully or not at all (no partial states).
    // Visibility: Changes by one thread are seen by others (solved by volatile/locks).
    // Mutual Exclusion: Only one thread accesses a resource at a time.

    // Deadlock example: Thread 1 locks A then B. Thread 2 locks B then A.
    // Deadlock Prevention Strategy:
    // Always acquire multiple locks in a consistent globally defined order.

    // ============================================================
    // 35. DESIGN PATTERNS USED IN LLD
    // ============================================================
    
    // --- Creational ---
    // Factory: Return different subclasses based on input.
    // Builder: Chain methods to construct complex objects.
    
    // --- Behavioral ---
    // Strategy: Inject behavior.
    interface SortStrategy { void sort(List<Integer> list); }
    
    // Observer: Publish/Subscribe
    interface Observer { void update(String msg); }
    static class Subject {
        List<Observer> obs = new ArrayList<>();
        void notifyAll(String msg) { obs.forEach(o -> o.update(msg)); }
    }

    // --- Structural ---
    // Decorator: Wrap an object to add features dynamically.
    // Adapter: Wrap an incompatible interface to match expected interface.

    // ============================================================
    // 36. LLD CLASS DESIGN TEMPLATE
    // ============================================================
    // A clean setup showing composition, dependency injection, and interface use.

    interface PaymentProcessor {
        void process(double amount);
    }

    static class StripePayment implements PaymentProcessor {
        @Override
        public void process(double amount) {
            System.out.println("Processing via Stripe: " + amount);
        }
    }

    static class PaymentService {
        private final PaymentProcessor processor; // Composition

        // Dependency Injection via constructor
        public PaymentService(PaymentProcessor processor) {
            this.processor = processor;
        }

        public void checkout(double amount) {
            processor.process(amount);
        }
    }

    // ============================================================
    // 37. MULTITHREADED LLD TEMPLATE
    // ============================================================
    // Simple thread-safe task manager skeleton

    static class Task {
        int id; String name;
        Task(int id, String name) { this.id = id; this.name = name; }
    }

    static class TaskManager {
        private final ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();
        private final AtomicInteger idGenerator = new AtomicInteger(0);
        private final ExecutorService executor = Executors.newFixedThreadPool(4);

        public void submitTask(String name) {
            int taskId = idGenerator.incrementAndGet();
            Task task = new Task(taskId, name);
            tasks.put(taskId, task);

            executor.submit(() -> {
                try {
                    System.out.println("Executing task: " + task.name);
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    tasks.remove(taskId);
                }
            });
        }

        public void shutdown() {
            executor.shutdown();
        }
    }
}

// ============================================================
// 38. LLD JAVA CHECKLIST
// ============================================================
//
// [ ] class / object
// [ ] interface
// [ ] abstract class
// [ ] inheritance
// [ ] composition
// [ ] enum
// [ ] encapsulation
// [ ] polymorphism
// [ ] ArrayList
// [ ] HashMap
// [ ] HashSet
// [ ] TreeMap
// [ ] TreeSet
// [ ] Queue
// [ ] Deque
// [ ] PriorityQueue
// [ ] Comparator
// [ ] Generics
// [ ] equals()
// [ ] hashCode()
// [ ] StringBuilder
// [ ] Exception handling
// [ ] Thread
// [ ] Runnable
// [ ] synchronized
// [ ] volatile
// [ ] AtomicInteger
// [ ] Lock
// [ ] ReentrantLock
// [ ] Condition
// [ ] wait/notify
// [ ] ExecutorService
// [ ] Callable
// [ ] Future
// [ ] CompletableFuture
// [ ] CountDownLatch
// [ ] CyclicBarrier
// [ ] Semaphore
// [ ] ConcurrentHashMap
// [ ] BlockingQueue
// [ ] Design Patterns
// [ ] Thread safety
// [ ] Race condition
// [ ] Deadlock