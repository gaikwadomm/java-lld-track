package patterns.behavioral;

import java.util.*;

interface Subscriber {
    void update(String videoTitle);
}

class EmailSubscriber implements Subscriber {
    private String email;

    public EmailSubscriber(String email){
        this.email = email;
    }

    @Override
    public void update(String videoTitle){
        System.out.println("To [ "+email+" ] : Video : "+videoTitle+" get uploaded!");
    }
}

class MobileSubsciber implements Subscriber {
    private String username;

    public MobileSubsciber(String username){
        this.username = username;
    }

    @Override
    public void update(String videTitle){
        System.out.println("In-app Mobile Notification to [ "+username+" ] : Video : "+videTitle+" get uploaded!");
    }
}


interface Channel {
    void subscribe(Subscriber sub);
    void unsubscribe(Subscriber sub);
    void notifySubscribers(String videoTitle);
    void uploadVideo(String videoTitle);
}

class YoutubeChannel implements Channel {
    private List<Subscriber> subscribers = new ArrayList<>();
    private String channelName;

    public YoutubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Subscriber sub){
        subscribers.add(sub);
    }

    @Override
    public void unsubscribe(Subscriber sub){
        subscribers.remove(sub);
    }

    @Override
    public void notifySubscribers(String videoTitle){
        for(Subscriber sub: subscribers){
            sub.update(videoTitle);
        }
    }

    @Override
    public void uploadVideo(String videoTitle){
        System.out.println(channelName + " uploaded: " + videoTitle + "\n");
        notifySubscribers(videoTitle);
    }
}

public class Observer {
    public static void main(String args[]){
        Channel c1 = new YoutubeChannel("Omniverse");

        c1.subscribe(new EmailSubscriber("om@nomail.com"));
        c1.subscribe(new EmailSubscriber("yash@nomail.com"));
        c1.subscribe(new MobileSubsciber("07omgaikwad"));

        c1.uploadVideo("Binary Lifting Part-1");
    }
}

