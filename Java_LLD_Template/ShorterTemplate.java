import java.util.*;
import java.time.*;

// ============================================================
// C++ STL -> JAVA MAPPING (QUICK REFERENCE)
// ============================================================
// vector<int>              -> List<Integer> list = new ArrayList<>();
// unordered_map<K,V>       -> Map<K, V> map = new HashMap<>();
// map<K,V>                 -> TreeMap<K, V> map = new TreeMap<>();
// unordered_set<K>         -> Set<K> set = new HashSet<>();
// set<K>                   -> TreeSet<K> set = new TreeSet<>();
// queue<int>               -> Queue<Integer> q = new ArrayDeque<>();
// deque/stack              -> Deque<Integer> dq = new ArrayDeque<>();
// priority_queue<int>      -> PriorityQueue<Integer> pq = new PriorityQueue<>(); (Min-Heap by default)
// priority_queue (Max)     -> PriorityQueue<Integer> maxPq = new PriorityQueue<>(Collections.reverseOrder());
// pair                     -> record Pair(int x, int y) {} OR Custom Class

public class LLDJavaTemplate {

    // ============================================================
    // 1. CLASSES, INTERFACES, ENUMS & OOP (CORE FOR LLD)
    // ============================================================
    
    // Interface (Contract)
    interface PaymentStrategy {
        void pay(double amount);
    }

    // Implementation
    static class UPIPayment implements PaymentStrategy {
        @Override
        public void pay(double amount) {
            System.out.println("Paid via UPI: " + amount);
        }
    }

    // Abstract Class (Base Entity)
    abstract static class Vehicle {
        protected String id;
        public Vehicle(String id) { this.id = id; }
        abstract double calculateRate();
    }

    // Inheritance
    static class Car extends Vehicle {
        public Car(String id) { super(id); }
        @Override
        double calculateRate() { return 50.0; }
    }

    // Enum (Constants with behavior/values)
    enum OrderStatus {
        CREATED(1), PAID(2), DELIVERED(3);
        private final int code;
        OrderStatus(int code) { this.code = code; }
        public int getCode() { return code; }
    }

    // ============================================================
    // 2. EQUALS & HASHCODE (CRUCIAL FOR HASHMAP/HASHSET)
    // ============================================================
    static class User {
        private final int id;
        private final String email;

        public User(int id, String email) {
            this.id = id;
            this.email = email;
        }

        // If you use this object as a Key in HashMap or inside a HashSet,
        // you MUST override equals() and hashCode().
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return id == user.id && Objects.equals(email, user.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, email);
        }
    }

    // ============================================================
    // 3. ESSENTIAL COLLECTIONS (STL EQUIVALENTS)
    // ============================================================
    public void collectionsDemo() {
        // --- ArrayList (vector) ---
        List<String> list = new ArrayList<>();
        list.add("A");
        list.get(0);
        list.remove(0); // or remove("A")
        list.size();

        // --- HashMap (unordered_map) ---
        Map<String, User> userMap = new HashMap<>();
        userMap.put("user1", new User(1, "a@a.com"));
        userMap.get("user1");
        userMap.containsKey("user1");
        userMap.remove("user1");
        
        // Iteration
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            entry.getKey();
            entry.getValue();
        }
        
        // IMPORTANT LLD PATTERN: Grouping / Adjacency List
        Map<Integer, List<String>> adjList = new HashMap<>();
        adjList.computeIfAbsent(1, k -> new ArrayList<>()).add("Node2");

        // --- HashSet (unordered_set) ---
        Set<Integer> set = new HashSet<>();
        set.add(10);
        set.contains(10);

        // --- TreeMap (map) - Sorted Keys ---
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(10, "Ten");
        treeMap.firstKey();     // Smallest key
        treeMap.lastKey();      // Largest key
        treeMap.ceilingKey(5);  // >= 5

        // --- Queue & Deque ---
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(1); // push back
        q.poll();   // pop front
        q.peek();   // front element
        
        // --- PriorityQueue (heap) ---
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(5); // push
        minHeap.poll();   // pop
    }

    // ============================================================
    // 4. COMPARATORS AND SORTING
    // ============================================================
    static class Item { 
        int price; 
        String name; 
        Item(int p, String n) { this.price = p; this.name = n; }
    }

    public void sortingDemo() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(10, "B"));
        items.add(new Item(5, "A"));

        // Lambda Sorting (C++ lambda equivalent)
        items.sort((a, b) -> Integer.compare(a.price, b.price));

        // Priority Queue with Custom Object (Max Heap based on price)
        PriorityQueue<Item> maxHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.price, a.price) 
        );

        // Comparator Utility (Cleanest for LLD)
        items.sort(Comparator.comparingInt((Item i) -> i.price)
                             .thenComparing(i -> i.name));
    }

    // ============================================================
    // 5. EXCEPTIONS (VALIDATION IN LLD)
    // ============================================================
    // Custom exception for business logic failures
    static class InvalidBookingException extends RuntimeException {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    public void bookTicket(int availableSeats) {
        if (availableSeats <= 0) {
            throw new InvalidBookingException("No seats available");
        }
    }

    // ============================================================
    // 6. STRINGS & STRINGBUILDER
    // ============================================================
    public void stringsDemo() {
        String s = "Test";
        // Always use .equals() for strings, NEVER ==
        if (s.equals("Test")) { }
        
        String[] parts = "A,B,C".split(",");
        
        // StringBuilder (For concatenations in loops)
        StringBuilder sb = new StringBuilder();
        sb.append("Order").append("-").append("123");
        String result = sb.toString();
    }

    // ============================================================
    // 7. SINGLETON PATTERN (ESSENTIAL CREATIONAL PATTERN)
    // ============================================================
    // Easiest, thread-safe way to create a Singleton in Java
    enum IdGenerator {
        INSTANCE;
        private int count = 0;
        public int getNextId() { return ++count; }
    }

    // ============================================================
    // 8. LLD SKELETON EXAMPLE (Putting it together)
    // ============================================================
    static class ParkingSystem {
        private final Map<String, Vehicle> parkedVehicles; // Encapsulated state
        
        public ParkingSystem() {
            this.parkedVehicles = new HashMap<>();
        }
        
        public void parkVehicle(Vehicle vehicle) {
            if (parkedVehicles.containsKey(vehicle.id)) {
                throw new RuntimeException("Vehicle already parked!");
            }
            parkedVehicles.put(vehicle.id, vehicle);
            System.out.println("Parked: " + vehicle.id);
        }
        
        public void removeVehicle(String vehicleId) {
            Vehicle v = parkedVehicles.remove(vehicleId);
            if (v == null) {
                throw new RuntimeException("Vehicle not found!");
            }
        }
    }
}