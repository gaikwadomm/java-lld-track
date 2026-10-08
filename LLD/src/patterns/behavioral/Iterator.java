package patterns.behavioral;
import java.util.*;

//Good But not that Ideal

// import java.util.*;

// // ========== Video class representing a single video ==========
// class Video {
//     private String title;

//     public Video(String title) {
//         this.title = title;
//     }

//     public String getTitle() {
//         return title;
//     }
// }

// // ========== YouTubePlaylist class (Aggregate) ==========
// class YouTubePlaylist {
//     private List<Video> videos = new ArrayList<>();

//     // Method to add video to playlist
//     public void addVideo(Video video) {
//         videos.add(video);
//     }

//     // Method to expose internal video list 
//     public List<Video> getVideos() {
//         return videos;
//     }
// }

// // ========== Iterator interface ==========
// interface PlaylistIterator {
//     boolean hasNext();
//     Video next();
// }

// // ========== Concrete Iterator class ==========
// class YouTubePlaylistIterator implements PlaylistIterator {
//     private List<Video> videos;
//     private int position;

//     // Constructor takes the list to iterate on
//     public YouTubePlaylistIterator(List<Video> videos) {
//         this.videos = videos;
//         this.position = 0;
//     }

//     // Check if more videos are left to iterate
//     @Override
//     public boolean hasNext() {
//         return position < videos.size();
//     }

//     // Return the next video in sequence
//     @Override
//     public Video next() {
//         return hasNext() ? videos.get(position++) : null;
//     }
// }

// // ========== Main method (Client code) ==========
// public class Iterator {
//     public static void main(String[] args) {
//         // Create a playlist and add videos
//         YouTubePlaylist playlist = new YouTubePlaylist();
//         playlist.addVideo(new Video("LLD Tutorial"));
//         playlist.addVideo(new Video("System Design Basics"));

//         // Client directly creates the iterator using internal list (not ideal)
//         PlaylistIterator iterator = new YouTubePlaylistIterator(playlist.getVideos());

//         // Use the iterator to loop through the playlist
//         while (iterator.hasNext()) {
//             System.out.println(iterator.next().getTitle());
//         }
//     }
// }




// class Video {
//     private String title;

//     public Video(String title){
//         this.title = title;
//     }

//     public String getTitle(){
//         return this.title;
//     }
// }

// interface Playlist {
//     PlaylistIterator getIterator();
// }

// class YouTubePlaylist implements Playlist {
//     private List<Video> videos = new ArrayList<>();
//     private String name;

//     public YouTubePlaylist(String name){
//         this.name = name;
//     }

//     public void addVideo(Video video){
//         videos.add(video);
//     }

//     public PlaylistIterator getIterator(){
//         return new YouTubePlaylistIterator(videos);
//     }
// }

// interface PlaylistIterator{
//     boolean hasNext();
//     Video next();
// }

// class YouTubePlaylistIterator implements PlaylistIterator {
//     private List<Video> videos;
//     private int position;

//     public YouTubePlaylistIterator(List<Video> videos){
//         this.videos = videos;
//         this.position = 0;
//     }

//     @Override
//     public boolean hasNext(){
//         return position<videos.size();
//     }

//     @Override
//     public Video next(){
//         return videos.get(position++);
//     }
// }

// public class Iterator{
//     public static void main(String args[]){
//         Video v1 = new Video("Segment Tree Video");
//         Video v2 = new Video("Segment Tree Part 2");

//         YouTubePlaylist v = new YouTubePlaylist("Segement Tree Playlist");
//         v.addVideo(v1);
//         v.addVideo(v2);

//         PlaylistIterator iterator = v.getIterator();

//         while(iterator.hasNext()){
//             System.out.println(iterator.next().getTitle());
//         }
//     }
// }

// ============================================================
// WITH MULTIPLE TYPES OF PLAYLIST LIKE VIDEO, SONGS AND LIKE 
// ============================================================

// interface Media {
//     String getTitle();
// }

// // 2. Your Video class, now implementing Media
// class Video implements Media {
//     private String title;

//     public Video(String title){
//         this.title = title;
//     }

//     @Override
//     public String getTitle(){
//         return "[YouTube Video] " + this.title;
//     }
// }

// // 3. A new Song class, implementing Media
// class Song implements Media {
//     private String title;
//     private String platform; // To distinguish between YouTube Song and Spotify Song

//     public Song(String title, String platform){
//         this.title = title;
//         this.platform = platform;
//     }

//     @Override
//     public String getTitle(){
//         return "[" + this.platform + " Song] " + this.title;
//     }
// }

// interface Playlist {
//     PlaylistIterator getIterator();
// }

// interface PlaylistIterator {
//     boolean hasNext();
//     Media nextMedia();
// }

// // 6. YouTube Playlist (can hold videos or songs)
// class YouTubePlaylist implements Playlist {
//     private List<Media> mediaList = new ArrayList<>();
//     private String name;

//     public YouTubePlaylist(String name){
//         this.name = name;
//     }

//     public void addMedia(Media media){
//         mediaList.add(media);
//     }

//     @Override
//     public PlaylistIterator getIterator(){
//         return new CustomPlaylistIterator(mediaList);
//     }
// }

// // 7. Spotify Playlist (holds songs)
// class SpotifyPlaylist implements Playlist {
//     private List<Media> mediaList = new ArrayList<>();
//     private String name;

//     public SpotifyPlaylist(String name){
//         this.name = name;
//     }

//     public void addMedia(Media media){
//         mediaList.add(media);
//     }

//     @Override
//     public PlaylistIterator getIterator(){
//         return new CustomPlaylistIterator(mediaList);
//     }
// }

// // 8. Your custom Iterator logic (works for any playlist using a List)
// class CustomPlaylistIterator implements PlaylistIterator {
//     private List<Media> mediaList;
//     private int position;

//     public CustomPlaylistIterator(List<Media> mediaList){
//         this.mediaList = mediaList;
//         this.position = 0;
//     }

//     @Override
//     public boolean hasNext(){
//         return position < mediaList.size();
//     }

//     @Override
//     public Media nextMedia(){
//         return mediaList.get(position++);
//     }
// }

// // 9. Main Execution
// public class Iterator {
//     public static void main(String args[]){
        
//         // --- 1. Playlist of Videos ---
//         YouTubePlaylist videoPlaylist = new YouTubePlaylist("Algorithm Tutorials");
//         videoPlaylist.addMedia(new Video("Segment Tree Part 1"));
//         videoPlaylist.addMedia(new Video("Segment Tree Part 2"));

//         // --- 2. Playlist of Songs (Mixing YouTube and Spotify) ---
//         SpotifyPlaylist songPlaylist = new SpotifyPlaylist("Coding Focus Mix");
//         songPlaylist.addMedia(new Song("Lofi Beats", "YouTube"));
//         songPlaylist.addMedia(new Song("White Noise", "Spotify"));
//         songPlaylist.addMedia(new Song("Synthwave", "Spotify"));

//         // --- Iterating the Video Playlist ---
//         System.out.println("--- " + "Algorithm Tutorials" + " ---");
//         PlaylistIterator videoIterator = videoPlaylist.getIterator();
        
//         while(videoIterator.hasNext()){
//             System.out.println(videoIterator.nextMedia().getTitle());
//         }

//         System.out.println();

//         // --- Iterating the Song Playlist ---
//         System.out.println("--- " + "Coding Focus Mix" + " ---");
//         PlaylistIterator songIterator = songPlaylist.getIterator();
        
//         while(songIterator.hasNext()){
//             System.out.println(songIterator.nextMedia().getTitle());
//         }
//     }
// }


// 1. Common interface for all media types
interface MediaItem {
    String getTitle();
}

// 2. Concrete Media Items
class Video implements MediaItem {
    private String title;
    public Video(String title) { this.title = title; }
    
    @Override
    public String getTitle() { return "[Video] " + this.title; }
}

class Song implements MediaItem {
    private String title;
    private String artist;
    public Song(String title, String artist) { 
        this.title = title; 
        this.artist = artist; 
    }
    
    @Override
    public String getTitle() { return "[Song] " + this.title + " by " + this.artist; }
}

// 3. Generic Iterator Interfaces
interface PlaylistIterator<T> {
    boolean hasNext();
    T next();
}

interface Playlist<T> {
    PlaylistIterator<T> getIterator();
}

// ==========================================
// YOUTUBE PLAYLIST (Backed by an ArrayList)
// ==========================================
class YouTubePlaylist<T> implements Playlist<T> {
    private List<T> items = new ArrayList<>();
    private String name;

    public YouTubePlaylist(String name) { this.name = name; }
    public void addItem(T item) { items.add(item); }

    @Override
    public PlaylistIterator<T> getIterator() {
        return new YouTubePlaylistIterator<>(items);
    }
}

class YouTubePlaylistIterator<T> implements PlaylistIterator<T> {
    private List<T> items;
    private int position = 0;

    public YouTubePlaylistIterator(List<T> items) { this.items = items; }

    @Override
    public boolean hasNext() { return position < items.size(); }

    @Override
    public T next() { return items.get(position++); }
}

// ==========================================
// SPOTIFY PLAYLIST (Backed by a standard Array)
// ==========================================
class SpotifyPlaylist<T> implements Playlist<T> {
    private T[] items;
    private int position = 0;
    private String name;

    @SuppressWarnings("unchecked")
    public SpotifyPlaylist(String name, int capacity) {
        this.name = name;
        // Creating a generic array requires casting in Java
        this.items = (T[]) new Object[capacity]; 
    }

    public void addItem(T item) {
        if (position < items.length) {
            items[position++] = item;
        } else {
            System.out.println("Spotify playlist is full!");
        }
    }

    @Override
    public PlaylistIterator<T> getIterator() {
        return new SpotifyPlaylistIterator<>(items);
    }
}

class SpotifyPlaylistIterator<T> implements PlaylistIterator<T> {
    private T[] items;
    private int position = 0;

    public SpotifyPlaylistIterator(T[] items) { this.items = items; }

    @Override
    public boolean hasNext() {
        // Stop if we reach the end of the array or hit a null spot
        return position < items.length && items[position] != null;
    }

    @Override
    public T next() { return items[position++]; }
}

// ==========================================
// CLIENT CODE
// ==========================================
public class Iterator {
    public static void main(String[] args) {
        
        // 1. Create a YouTube Playlist for Videos
        YouTubePlaylist<Video> dsAlgoPlaylist = new YouTubePlaylist<>("DS & Algo Videos");
        dsAlgoPlaylist.addItem(new Video("Segment Tree Part 1"));
        dsAlgoPlaylist.addItem(new Video("Segment Tree Part 2"));

        // 2. Create a YouTube Playlist for Songs
        YouTubePlaylist<Song> ytMusicPlaylist = new YouTubePlaylist<>("LoFi Coding Music");
        ytMusicPlaylist.addItem(new Song("Chill Vibes", "Lofi Girl"));
        
        // 3. Create a Spotify Playlist for Songs (Capacity of 3)
        SpotifyPlaylist<Song> spotifyPlaylist = new SpotifyPlaylist<>("Gym Mix", 3);
        spotifyPlaylist.addItem(new Song("Eye of the Tiger", "Survivor"));
        spotifyPlaylist.addItem(new Song("Stronger", "Kanye West"));

        // Print them out using our Iterators
        System.out.println("--- YouTube Video Playlist ---");
        printPlaylist(dsAlgoPlaylist.getIterator());

        System.out.println("\n--- YouTube Song Playlist ---");
        printPlaylist(ytMusicPlaylist.getIterator());

        System.out.println("\n--- Spotify Song Playlist ---");
        printPlaylist(spotifyPlaylist.getIterator());
    }

    // Notice how this method doesn't care if it's an Array or an ArrayList!
    // It only depends on the PlaylistIterator interface.
    private static void printPlaylist(PlaylistIterator<? extends MediaItem> iterator) {
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getTitle());
        }
        System.out.println();
    }
}