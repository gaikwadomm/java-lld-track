// package patterns.behavioral;

// import java.util.*;

// // OrderContext class manages the current state of the order
// class OrderContext {
//     private OrderState currentState;

//     // Constructor initializes the state to ORDER_PLACED
//     public OrderContext() {
//         this.currentState = new OrderPlacedState(); // default state
//     }

//     // Method to set a new state for the order
//     public void setState(OrderState state) {
//         this.currentState = state;
//     }

//     // // Method to move the order to the next state
//     public void next() {
//         currentState.next(this);
//     }

//     // Method to cancel the order
//     public void cancel() {
//         currentState.cancel(this);
//     }

//     // Method to get the current state of the order
//     public String getCurrentState() {
//         return currentState.getStateName();
//     }
// }

// // OrderState interface defines the behavior of the order states
// interface OrderState {
//     void next(OrderContext context); // Move to the next state
//     void cancel(OrderContext context); // Cancel the order
//     String getStateName(); // Get the name of the state
// }

// // Concrete states for each stage of the order

// // OrderPlacedState handles the behavior when the order is placed
// class OrderPlacedState implements OrderState {
//     public void next(OrderContext context) {
//         context.setState(new PreparingState());
//         System.out.println("Order is now being prepared.");
//     }

//     public void cancel(OrderContext context) {
//         context.setState(new CancelledState());
//         System.out.println("Order has been cancelled.");
//     }

//     public String getStateName() {
//         return "ORDER_PLACED";
//     }
// }

// // PreparingState handles the behavior when the order is being prepared
// class PreparingState implements OrderState {
//     public void next(OrderContext context) {
//         context.setState(new OutForDeliveryState());
//         System.out.println("Order is out for delivery.");
//     }

//     public void cancel(OrderContext context) {
//         context.setState(new CancelledState());
//         System.out.println("Order has been cancelled.");
//     }

//     public String getStateName() {
//         return "PREPARING";
//     }
// }

// // OutForDeliveryState handles the behavior when the order is out for delivery
// class OutForDeliveryState implements OrderState {
//     public void next(OrderContext context) {
//         context.setState(new DeliveredState());
//         System.out.println("Order has been delivered.");
//     }

//     public void cancel(OrderContext context) {
//         System.out.println("Cannot cancel. Order is out for delivery.");
//     }

//     public String getStateName() {
//         return "OUT_FOR_DELIVERY";
//     }
// }

// // DeliveredState handles the behavior when the order is delivered
// class DeliveredState implements OrderState {
//     public void next(OrderContext context) {
//         System.out.println("Order is already delivered.");
//     }

//     public void cancel(OrderContext context) {
//         System.out.println("Cannot cancel a delivered order.");
//     }

//     public String getStateName() {
//         return "DELIVERED";
//     }
// }

// // CancelledState handles the behavior when the order is cancelled
// class CancelledState implements OrderState {
//     public void next(OrderContext context) {
//         System.out.println("Cancelled order cannot move to next state.");
//     }

//     public void cancel(OrderContext context) {
//         System.out.println("Order is already cancelled.");
//     }

//     public String getStateName() {
//         return "CANCELLED";
//     }
// }

// public class State {
//     public static void main(String[] args) {
//         OrderContext order = new OrderContext();

//         // Display initial state
//         System.out.println("Current State: " + order.getCurrentState());

//         // Moving through states
//         order.next();  // ORDER_PLACED -> PREPARING
//         order.next();  // PREPARING -> OUT_FOR_DELIVERY
//         order.cancel(); // Should fail, as order is out for delivery
//         order.next();  // OUT_FOR_DELIVERY -> DELIVERED
//         order.cancel(); // Should fail, as order is delivered

//         // Display final state
//         System.out.println("Final State: " + order.getCurrentState());
//     }
// }

package patterns.behavioral;

// Context Class
class OrderContext {
    private OrderState currentOrderState;

    public OrderContext() {
        // Use singleton instance instead of 'new'
        this.currentOrderState = OrderPlacedState.getInstance();
    }

    // Public API for clients
    public void next() {
        currentOrderState.next(this);
    }

    public void cancel() {
        currentOrderState.cancel(this);
    }

    public String getStateName() {
        return currentOrderState.getStateName();
    }

    // Protected so clients can't force invalid state changes
    protected void setOrderState(OrderState orderState) {
        this.currentOrderState = orderState;
    }
}

interface OrderState {
    void next(OrderContext order);
    void cancel(OrderContext order);
    String getStateName();
}

// Concrete States implemented as Singletons
class OrderPlacedState implements OrderState {
    private static final OrderPlacedState instance = new OrderPlacedState();
    private OrderPlacedState() {} // Private constructor
    public static OrderPlacedState getInstance() { return instance; }

    @Override
    public void next(OrderContext order) {
        order.setOrderState(PreparingState.getInstance());
        System.out.println("Order is now being prepared!");
    }

    @Override
    public void cancel(OrderContext context) {
        context.setOrderState(CancelledState.getInstance());
        System.out.println("Order has been cancelled.");
    }

    @Override
    public String getStateName() {
        return "ORDER_PLACED";
    }
}

class PreparingState implements OrderState {
    private static final PreparingState instance = new PreparingState();
    private PreparingState() {}
    public static PreparingState getInstance() { return instance; }

    @Override
    public void next(OrderContext order) {
        order.setOrderState(OutForDeliveryState.getInstance());
        System.out.println("Order is Out For Delivery!");
    }

    @Override
    public void cancel(OrderContext context) {
        context.setOrderState(CancelledState.getInstance());
        System.out.println("Order has been cancelled.");
    }

    @Override
    public String getStateName() {
        return "PREPARING";
    }
}

class OutForDeliveryState implements OrderState {
    private static final OutForDeliveryState instance = new OutForDeliveryState();
    private OutForDeliveryState() {}
    public static OutForDeliveryState getInstance() { return instance; }

    @Override
    public void next(OrderContext context) {
        context.setOrderState(DeliveredState.getInstance());
        System.out.println("Order has been delivered.");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Cannot cancel. Order is out for delivery.");
    }

    @Override
    public String getStateName() {
        return "OUT_FOR_DELIVERY";
    }
}

class DeliveredState implements OrderState {
    private static final DeliveredState instance = new DeliveredState();
    private DeliveredState() {}
    public static DeliveredState getInstance() { return instance; }

    @Override
    public void next(OrderContext context) {
        System.out.println("Order is already delivered.");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Cannot cancel a delivered order.");
    }

    @Override
    public String getStateName() {
        return "DELIVERED";
    }
}

class CancelledState implements OrderState {
    private static final CancelledState instance = new CancelledState();
    private CancelledState() {}
    public static CancelledState getInstance() { return instance; }

    @Override
    public void next(OrderContext context) {
        System.out.println("Cancelled order cannot move to next state.");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Order is already cancelled.");
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}

public class State {
    public static void main(String args[]) {
        OrderContext order = new OrderContext();

        System.out.println("Current State: " + order.getStateName());

        order.next();  // ORDER_PLACED -> PREPARING
        order.next();  // PREPARING -> OUT_FOR_DELIVERY
        order.cancel(); // Should fail
        order.next();  // OUT_FOR_DELIVERY -> DELIVERED
        order.cancel(); // Should fail

        System.out.println("Final State: " + order.getStateName());
    }
}