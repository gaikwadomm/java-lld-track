package patterns.structural;

import java.util.*;

interface VideoDownloader {
    String downloadVideo(String url);
}

class RealVideoDownloader implements VideoDownloader {
    @Override
    public String downloadVideo(String url){
        System.out.println("Downloading Video...");
        return "Video content from "+url;
    }
}


// Proxy 
// =============== Proxy With Cache (Lazy Initialization) ====================
class CachedVideoDownloader implements VideoDownloader {

    private RealVideoDownloader realDownloader;
    private Map<String, String> cache;

    public CachedVideoDownloader() {
        // We only initialize the cache here. 
        // We leave realDownloader as null.
        this.cache = new HashMap<>();
    }

    @Override
    public String downloadVideo(String videoUrl) {
        if (cache.containsKey(videoUrl)) {
            System.out.println("Returning cached video for: " + videoUrl);
            return cache.get(videoUrl);
        }

        System.out.println("Cache miss. Downloading...");
        
        // LAZY INITIALIZATION: Create the real downloader ONLY if we need it
        if (realDownloader == null) {
            System.out.println("Initializing RealVideoDownloader for the first time...");
            realDownloader = new RealVideoDownloader();
        }

        String video = realDownloader.downloadVideo(videoUrl);
        cache.put(videoUrl, video);
        return video;
    }
}

public class Proxy {
    public static void main(String args[]){
        VideoDownloader m1 = new CachedVideoDownloader();

        String user1Video = m1.downloadVideo("https://tuf.com");
        System.out.println(user1Video);

        String user2Video = m1.downloadVideo("https://tuf.com");
        System.out.println(user2Video);


    }
}
