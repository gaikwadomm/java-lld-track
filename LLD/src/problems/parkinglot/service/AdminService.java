package problems.parkinglot.service;

import problems.parkinglot.domain.Floor;
import problems.parkinglot.domain.ParkingSlot;
import problems.parkinglot.domain.PricingRule;
import problems.parkinglot.domain.Vehicle;
import problems.parkinglot.repository.FloorRepository;
import problems.parkinglot.repository.PricingRuleRepository;
import problems.parkinglot.repository.SlotRepository;
import java.util.List;
import java.util.Map;

public class AdminService {
    private FloorRepository floorRepository;
    private SlotRepository slotRepository;
    private PricingRuleRepository pricingRuleRepository;

    public AdminService(FloorRepository floorRepository, SlotRepository slotRepository, 
                       PricingRuleRepository pricingRuleRepository) {
        this.floorRepository = floorRepository;
        this.slotRepository = slotRepository;
        this.pricingRuleRepository = pricingRuleRepository;
        System.out.println("[SERVICE] AdminService initialized");
    }

    public void initializeParkingLot() {
        System.out.println("[SERVICE] Initializing parking lot with default configuration");
        
        // Create 3 floors
        for (int i = 0; i < 3; i++) {
            addFloor(i);
        }
        
        // Add slots to floor 0
        addSlotsToFloor(0, Vehicle.VehicleType.BIKE, 20);
        addSlotsToFloor(0, Vehicle.VehicleType.CAR, 30);
        addSlotsToFloor(0, Vehicle.VehicleType.TRUCK, 5);
        
        // Add slots to floor 1
        addSlotsToFloor(1, Vehicle.VehicleType.CAR, 40);
        addSlotsToFloor(1, Vehicle.VehicleType.EV, 10);
        
        // Add slots to floor 2
        addSlotsToFloor(2, Vehicle.VehicleType.CAR, 35);
        addSlotsToFloor(2, Vehicle.VehicleType.EV, 15);
        
        // Initialize default pricing rules
        initializeDefaultPricingRules();
        
        System.out.println("[SERVICE] Parking lot initialization completed");
    }


    private void addFloor(int floorNumber) {
        System.out.println("[SERVICE] Adding floor with number: " + floorNumber);
        
        if (floorRepository.existsByNumber(floorNumber)) {
            System.out.println("[REPOSITORY] Floor number " + floorNumber + " exists: true");
            return;
        }
        
        Floor floor = new Floor(floorNumber);
        floorRepository.save(floor);
        System.out.println("[SERVICE] Floor added successfully: " + floor.getId());
    }


    private void addSlotsToFloor(int floorNumber, Vehicle.VehicleType slotType, int count) {
        System.out.println("[SERVICE] Adding " + count + " slots of type " + slotType + " to floor " + floorNumber);
        
        Floor floor = floorRepository.findByNumber(floorNumber).orElseThrow(
            () -> new IllegalStateException("Floor " + floorNumber + " not found")
        );
        
        for (int i = 0; i < count; i++) {
            ParkingSlot slot = new ParkingSlot(slotType, floorNumber);
            slotRepository.save(slot);
            floor.addSlot(slot);
        }
        
        System.out.println("[SERVICE] Added " + count + " slots to floor " + floorNumber);
    }

}
