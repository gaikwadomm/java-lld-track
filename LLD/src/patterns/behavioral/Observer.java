package patterns.behavioral;

import java.util.*;

interface Subscriber {
    void update(String videoTitle);
}

class EmailSubscriber implements Subscriber {
    private String email;

    EmailSubscriber(String email){
        this.email = email;
    }

    @Override
    public void update(String videoTitle){
        System.out.println("Email Notification Video : "+videoTitle+" is uploaded!");
    }
}

class MobileSubscriber implements Subscriber {
    private String username;

    MobileSubscriber(String username){
        this.username = username;
    }

    @Override
    public void update(String videoTitle){
        System.out.println("Mobile Notification Video : "+videoTitle+" is uploaded!");
    }
}


interface Channel {
    void subscribe(Subscriber name);
    void unsubscribe(Subscriber name);
    void notifySubscribers(String videoTitle);
}

class YoutubeChannel implements Channel {
    private List<Subscriber> channelSubscribers = new ArrayList<>();
    private String channelName;

    YoutubeChannel(String channelName){
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Subscriber name){
        channelSubscribers.add(name);
    }

    @Override
    public void unsubscribe(Subscriber name){
        channelSubscribers.remove(name);
    }

    @Override
    public void notifySubscribers(String videoTitle){
        for(Subscriber subs : channelSubscribers){
            subs.update(videoTitle);
        }
    }

    public void uploadVideo(String videoTitle){
        System.out.println(channelName+" uploaded video on "+videoTitle);
        notifySubscribers(videoTitle);
    }
}

public class Observer {
    public static void main(String args[]){
        Subscriber s1 = new EmailSubscriber("om@gmail.com");

        YoutubeChannel y1 = new YoutubeChannel("My Channel");

        y1.subscribe(s1);

        y1.uploadVideo("CP Playlist");
    }
}
