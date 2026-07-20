package solid;

// Violating the Interface Segregation 
// Dont make the driver class to implement the unwanted payRide, bookRide
// And vice versa for Customer class

// interface Uber{
//     void bookRide();
//     void acceptRide();
//     void drive();
//     void endRide();
//     void payRide();
// }

// Instead Make Multiple interfaces

interface Driver{
    void acceptRide();
    void drive();
    void endRide();
}

interface Customer{
    void bookRide();
    void payRide();
}

class SuvDriver implements Driver {
    @Override
    public void acceptRide(){

    }

    public void drive(){

    }

    public void endRide(){

    }
}

class PrimeCustomer implements Customer{
    @Override
    public void bookRide(){

    }

    public void payRide(){

    }
}

public class InterfaceSeg {
    
}
