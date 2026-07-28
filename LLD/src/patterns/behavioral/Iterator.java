// package patterns.behavioral;
// import java.util.*;

// class Video {
//     private String title;

//     Video(String title){
//         this.title = title;
//     }

//     public String getTitle(){
//         return this.title;
//     }
// }

// class YoutubePlayList{
//     private List<Video> videos = new ArrayList<>();

//     void addVideo(Video v){
//         videos.add(v);
//     }

//     public List<Video> getVideos(){
//         return this.videos;
//     }
// }

// interface PlaylistIterator {
//     boolean hasNext();
//     Video next();
// }

// class YoutubePlayListIterator implements PlaylistIterator {
//     private List<Video> videos;
//     private int position;

//     // Constructor takes the list to iterate on
//     public YoutubePlayListIterator(List<Video> videos) {
//         this.videos = videos;
//         this.position = 0;
//     }

//     @Override
//     public boolean hasNext(){
//         return position < videos.size();
//     }

//     @Override
//     public Video next(){
//         return hasNext() ? videos.get(position++) : null;
//     }
// }

// public class Iterator {
//     public static void main(String[] args) {
//         Video v1 = new Video("DSA Memgraph");
//         Video v2 = new Video("Digit DP");
//         YoutubePlayList yt = new YoutubePlayList();

//         yt.addVideo(v1);
//         yt.addVideo(v2);

//         PlaylistIterator it = new YoutubePlayListIterator(yt.getVideos());

//         while(it.hasNext()){
//             System.out.println(it.next().getTitle());
//         }
//     }
// }

package patterns.behavioral;
import java.util.*;

class Video {
    private String title;

    Video(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }
}

interface PlaylistAggregate  {
    YoutubePlayListIterator createIterator();
}

class YoutubePlayList implements PlaylistAggregate {
    private List<Video> videos = new ArrayList<>();

    void addVideo(Video v){
        videos.add(v);
    }

    public YoutubePlayListIterator createIterator(){
        return new YoutubePlayListIterator(videos);
    }
}

interface PlaylistIterator {
    boolean hasNext();
    Video next();
}

class YoutubePlayListIterator implements PlaylistIterator {
    private List<Video> videos;
    private int position;

    // Constructor takes the list to iterate on
    public YoutubePlayListIterator(List<Video> videos) {
        this.videos = videos;
        this.position = 0;
    }

    @Override
    public boolean hasNext(){
        return position < videos.size();
    }

    @Override
    public Video next(){
        return hasNext() ? videos.get(position++) : null;
    }
}

public class Iterator {
    public static void main(String[] args) {
        Video v1 = new Video("DSA Memgraph");
        Video v2 = new Video("Digit DP");
        YoutubePlayList yt = new YoutubePlayList();

        yt.addVideo(v1);
        yt.addVideo(v2);

        YoutubePlayListIterator it = yt.createIterator();

        while(it.hasNext()){
            System.out.println(it.next().getTitle());
        }
    }
}