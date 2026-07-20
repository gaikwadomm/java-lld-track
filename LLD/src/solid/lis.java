package solid;

import java.util.Scanner;

class Notification{
    public void sendNotification(){
        System.out.println("Send Email");
    }
}

class WhatsappNotification extends Notification{
    @Override
    public void sendNotification(){
        System.out.println("Send Message");
    }
}

public class lis {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        Notification sendWp = new WhatsappNotification();

        sendWp.sendNotification();

        scanner.close();
    }    
}
