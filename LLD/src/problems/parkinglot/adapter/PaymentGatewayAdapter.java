package problems.parkinglot.adapter;

import java.util.UUID;

public interface PaymentGatewayAdapter {
    boolean pay(UUID ticketId, double amount);
} 