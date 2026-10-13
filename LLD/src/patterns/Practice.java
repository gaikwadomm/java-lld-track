package patterns;

import java.lang.reflect.InvocationHandler;
import java.rmi.Remote;
import java.util.*;

import javax.annotation.processing.SupportedOptions;
import javax.management.RuntimeErrorException;
import javax.swing.event.CaretListener;

import patterns.behavioral.Command;


//------------------------------------------------
// CREATIONAL PATTERN -- START (SFABP)
//------------------------------------------------

//------------------------------------------------
// SINGLETON
//------------------------------------------------
class EagerLoading {
    private static final EagerLoading instance = new EagerLoading();

    private EagerLoading(){
        System.out.println("Eager Loading Get Instantiated ");
    }

    public static EagerLoading getInstace(){
        return instance;
    }
}

class LazyLoading{
    private static LazyLoading instance;

    private LazyLoading(){

    }

    public static LazyLoading getInstace(){
        if(instance==null){
            return instance = new LazyLoading();
        }
        return instance;
    }  
}

class DoubleChkSync{
    private static volatile DoubleChkSync instance;

    private DoubleChkSync(){

    }

    public  static DoubleChkSync getInstace(){
        if(instance==null){
            synchronized(DoubleChkSync.class){
                if(instance==null){
                    instance = new DoubleChkSync();
                }
            }
        }

        return instance;
    }
}

class BillPughSingleton{
    private  BillPughSingleton(){

    }

    private static class CreateInstance{
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance(){
        return CreateInstance.INSTANCE;
    }
}

//------------------------------------------------
// FACTORY
//------------------------------------------------

interface TravelService{
    void modeOfTravel();
}

class Air implements TravelService {
    @Override 
    public void modeOfTravel(){
        System.out.println("You are travelling by Air Mode");
    }
}

class Road implements TravelService{
    @Override 
    public  void modeOfTravel(){
        System.out.println("You are travelling by Road Mode");
    }
}

class TravelFactory {
    public static TravelService getTravelMode(String mode){
        if(mode.equalsIgnoreCase("AIR")){
            return new Air();
        }
        else if(mode.equalsIgnoreCase("ROAD")){
            return  new Road();
        }
        throw new IllegalArgumentException("Unknown Mode Provided : "+mode);
    }
}

//------------------------------------------------
// ABSTRACT FACTORY
//------------------------------------------------

interface PaymentGateway {
    void processPayment();
}
interface Invoice{
    void generateInvoice();
}

// India
class RazorPayGateway implements  PaymentGateway{
    @Override
    public void processPayment() {
        System.out.println("Razorpay Payment Processing");
    }
}
class PayUGateway implements  PaymentGateway{
    @Override
    public void processPayment() {
        System.out.println("PayU Payment Processing");
    }
}

class GSTInvoice implements Invoice{
    @Override
    public void generateInvoice() {
        System.out.println("GST Invoice Generated");
    }
}   

// Usa
class PayPal implements  PaymentGateway{
    @Override
    public void processPayment() {
       System.out.println("PayPal Payment Processing"); 
    }
}
class Stripe implements  PaymentGateway{
    @Override
    public void processPayment() {
        System.out.println("Stripe Payment Processing");
    }
}
class UsaInvoice implements Invoice{
    @Override
    public void generateInvoice() {
        System.out.println("USA Invoice Generated");
    }
}


interface RegionalFactory{
    PaymentGateway createPaymentGateway(String gatewayType);
    Invoice createInvoice();
}

// INDIAN FACTORY;
class IndiaFactory implements RegionalFactory {
    @Override
    public PaymentGateway createPaymentGateway(String getewayType) {
        if(getewayType.equalsIgnoreCase("razorpay")){
            return new RazorPayGateway();
        }
        else if(getewayType.equalsIgnoreCase("payu"));
        throw new IllegalArgumentException("Invalid Gate Way Type : "+getewayType);
    }

    @Override
    public Invoice createInvoice() {
        return  new GSTInvoice();
    }
}

class USFactory implements RegionalFactory {
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("paypal")) {
            return new PayPal();
        } else if (gatewayType.equalsIgnoreCase("stripe")) {
            return new Stripe();
        }
        throw new IllegalArgumentException("Unsupported gateway for US: " + gatewayType);
    }
 
    public Invoice createInvoice() {
        return new UsaInvoice();
    }
}


class CheckOutService{
    private PaymentGateway paymentGateway;
    private Invoice invoice;
    private String gatewayType;

    public CheckOutService(RegionalFactory factory, String gatewayType){
        this.gatewayType = gatewayType;
        this.paymentGateway = factory.createPaymentGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void completeOrder(double amount){
        paymentGateway.processPayment();
        invoice.generateInvoice();
    }
}


//------------------------------------------------
// BUILDER PATTERN
//------------------------------------------------

class BurgerMeal {
    // Compulsory
    private final String bunType;
    private final String pattyType;

    // Optional
    private final boolean withCheese;
    private final List<String> topings;
    private final String drink;
    private final String sides;

    private BurgerMeal(BurgerBuilder meal){
        this.bunType = meal.bunType;
        this.pattyType = meal.pattyType;
        this.withCheese = meal.withCheese;
        this.topings = meal.topings;
        this.drink = meal.drink;
        this.sides = meal.sides;
    }

    public static class BurgerBuilder{
        // Compulsory
        private final String bunType;
        private final String pattyType;

        // Optional
        private boolean withCheese;
        private List<String> topings;
        private String drink;
        private String sides;

        BurgerBuilder(String bunType, String pattyType){
            this.bunType = bunType;
            this.pattyType = pattyType;
        }

        public BurgerBuilder buildWithCheese(boolean withCheese){
            this.withCheese = withCheese;
            return this;
        }

        public BurgerBuilder addTopings(List<String> topings){
            this.topings = topings;
            return this;
        }

        public BurgerBuilder addDring(String drink){
            this.drink = drink;
            return this;
        }

        public BurgerBuilder addSides(String sides){
            this.sides = sides;
            return this;
        }

        public BurgerMeal build(){
            return new BurgerMeal(this);
        }
    }
}

class orderBurger{
    public static void placeOrder(){
        List<String> topings = Arrays.asList("tomatos","onion", "olives");
        BurgerMeal byBurger = new BurgerMeal.BurgerBuilder("Whole Wheat", "mac")
        .addTopings(topings)
        .build();
    }
}

//------------------------------------------------
// PROTOTYPE PATTERN
//------------------------------------------------

interface EmailTemplate extends  Cloneable {
    EmailTemplate clone();
    void setContent(String content);
    void sendTo(String to);
}

class WelcomeEmail implements EmailTemplate {
    private String subject;
    private String content;
    private String to;

    public  WelcomeEmail(){
        this.subject = "Welcome to ShopyAd";
        this.content = "Hey, shop start Advertising shop digitally!";
    }
 
    @Override 
    public EmailTemplate clone(){
            try {
                return (WelcomeEmail) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Clone Failed : ", e);
            }
    } 

    @Override 
    public void setContent(String content){
        this.content = content;
    }

    @Override 
    public void sendTo(String to){
        this.to = to;
    }
}

class EmailRegistry {
    public static final Map<String, EmailTemplate> emailTemplates = new HashMap<>();

    static {
        emailTemplates.put("welcome", new WelcomeEmail());
    }

    public static EmailTemplate getTemplate(String type){
        return emailTemplates.get(type).clone();
    }
}

//------------------------------------------------
// CREATIONAL PATTERNS -- END
//------------------------------------------------

//------------------------------------------------
// STRUCTURAL PATTERNS -- START (ABCDPFF)
//------------------------------------------------

//------------------------------------------------
// ADAPTER PATTERN
//------------------------------------------------

interface PayGateway{
    void pay(String invoiceId, double amount);
}

class PayU implements PayGateway {
    @Override
    public void pay(String invoice, double amount){
        System.out.println("Payment of "+amount+" get done by PayU Gateway!");
    }
}

class Razorpay {
    public void makePayment(String id, double amount){
        System.out.println("Payment of "+amount+" get done by Razorpay Gateway!");
    }
}

class RazorPayAdapter implements PayGateway{
    private Razorpay razorPayGateway;

    public RazorPayAdapter(){
        this.razorPayGateway = new Razorpay();
    }

    @Override
    public void pay(String invoiceId, double amount){
        razorPayGateway.makePayment(invoiceId, amount);
    }
}

// Client
class ChkOutService{
    private PayGateway paymentGateway;

    public ChkOutService(PayGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }

    public void checkout(String id, double amount){
        paymentGateway.pay(id, amount);
    }
}

//------------------------------------------------
// BRIDGE PATTERN
//------------------------------------------------

interface videoQualtiy{
    void display(String title);
}

class UltraHdQuality implements videoQualtiy{
    @Override 
    public  void display(String title){
        System.out.println("Ultra HD Qualtiy is in used to stream "+title+"!");
    }
}
class HdQualtiy implements videoQualtiy{
    @Override 
    public  void display(String title){
        System.out.println("HD Qualtiy is in used to stream "+title+"!");
    }
}
class SDQualtiy implements videoQualtiy{
    @Override 
    public  void display(String title){
        System.out.println("SD Qualtiy is in used to stream "+title+"!");
    }
}

// -- BRIDGE --
abstract class videoPlayer {
    protected videoQualtiy qualtiy;

    public videoPlayer(videoQualtiy qualtiy){
        this.qualtiy = qualtiy;
    }

    public abstract void play(String title);
}

class webPlayer extends videoPlayer {
    public webPlayer(videoQualtiy qualtiy){
        super(qualtiy);
    }

    @Override 
    public void play(String title){
        System.out.println("Playing on Web");
        super.qualtiy.display(title);
    }
}

class tvPlayer extends videoPlayer {
    public tvPlayer(videoQualtiy qualtiy){
        super(qualtiy);
    }

    @Override 
    public void play(String title){
        System.out.println("Playing on T.V");
        super.qualtiy.display(title);
    }
}

// Client
class clientPlaying {
    public static void client(){
        // TV with the ultra hd qualtiy
        videoPlayer tv = new tvPlayer(new UltraHdQuality());
        tv.play("Dhurandhar");
    }
}

//------------------------------------------------
// COMPOSITE PATTERN
//------------------------------------------------

interface CartItem {
    double getPrice();
    void display(int level);
}

class Product implements CartItem {
    private String name;
    private double price;

    public Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice(){
        return price;
    }

    @Override 
    public void display(int level){
        String indent = " ".repeat(level);
        System.out.println(indent + "Product : "+name+" Price : "+price);
    }
}

class ProductBundle implements CartItem {
    private String name;
    List<CartItem> productBundle = new ArrayList<>();

    public ProductBundle(String name){
        this.name = name;
    }

    public void addItem(CartItem item) {
        productBundle.add(item);
    }

    @Override 
    public double getPrice(){
        double total = 0;
        for(CartItem bundle : this.productBundle){
            total += bundle.getPrice();
        }

        return total;
    }

    @Override 
    public  void display(int level){
        String indent = "  ".repeat(level);
        System.out.println(indent + "Bundle: " + name);
        for(CartItem item : productBundle) {
            item.display(level + 1); 
        }
    }
}

class AmazonCart {
    public static void main(String[] args) {
        // Individual Products
        CartItem book = new Product("Atomic Habits", 499);
        CartItem phone = new Product("iPhone 15", 79999);
        CartItem earbuds = new Product("AirPods", 15999);
        CartItem charger = new Product("20W Charger", 1999);

        // Combo Deal
        ProductBundle iphoneCombo = new ProductBundle("iPhone Essentials Combo");
        iphoneCombo.addItem(phone);
        iphoneCombo.addItem(earbuds);
        iphoneCombo.addItem(charger);

        // Back to School Kit
        ProductBundle schoolKit = new ProductBundle("Back to School Kit");
        schoolKit.addItem(new Product("Notebook Pack", 249));
        schoolKit.addItem(new Product("Pen Set", 99));
        schoolKit.addItem(iphoneCombo);

        // Add everything to cart
        List<CartItem> cart = new ArrayList<>();
        cart.add(book);
        cart.add(schoolKit);

        // Display cart
        System.out.println("Your Amazon Cart:");
        double total = 0;
        for (CartItem item : cart) {
            // Start displaying at level 1
            item.display(1); 
            total += item.getPrice();
        }

        System.out.println("\nTotal: ₹" + total);
    }
}


//------------------------------------------------
// DECORATOR PATTERN
//------------------------------------------------

interface Pizza {
    String getDescription();
    double getPrice();
}

class PlainPizza implements Pizza {
    @Override 
    public String getDescription(){
        return "Plain pizza";
    }

    @Override 
    public  double getPrice(){
        return 150;
    }
}

class MargheritaPizza implements Pizza{
    @Override 
    public String getDescription(){
        return "Margherita pizza";
    }

    @Override 
    public  double getPrice(){
        return 200;
    } 
}

abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza){
        this.pizza = pizza;
    }
}

class ExtraCheese extends PizzaDecorator {
    public ExtraCheese(Pizza pizza){
        super(pizza);
    }

    @Override 
    public String getDescription(){
        return(pizza.getDescription() + ", Extra Cheese");
    }

    @Override 
    public double getPrice(){
        return(pizza.getPrice() + 20);
    }
}
class Olives extends PizzaDecorator {
    public Olives(Pizza pizza){
        super(pizza);
    }

    @Override 
    public String getDescription(){
        return(pizza.getDescription() + ", Olives");
    }

    @Override 
    public double getPrice(){
        return(pizza.getPrice() + 10);
    }
}
class StuffPizza extends PizzaDecorator {
    public StuffPizza(Pizza pizza){
        super(pizza);
    }

    @Override 
    public String getDescription(){
        return(pizza.getDescription() + ", Stuffed Pizza");
    }

    @Override 
    public double getPrice(){
        return(pizza.getPrice() + 40);
    }
}

class Dominos{
    public static void main(String[] args) {
        // Start with a basic Margherita Pizza
        Pizza myPizza = new MargheritaPizza();
 
        // Add Extra Cheese
        myPizza = new ExtraCheese(myPizza);
 
        // Add Olives
        myPizza = new Olives(myPizza);
 
        // Add Stuffed Crust
        myPizza = new StuffPizza(myPizza);
 
        // Final Description and Cost
        System.out.println("Pizza Description: " + myPizza.getDescription());
        System.out.println("Total Cost: ₹" + myPizza.getPrice());
    }
}


//------------------------------------------------
// PROXY PATTERN
//------------------------------------------------

interface VideoDownloader {
    String downloadVideo(String url);
}

class RealVideoDownloader implements VideoDownloader {
    @Override 
    public String downloadVideo(String url){
        System.out.println("Downloading video from URL: " + url);
        return "Video content from " + url;
    }
}

class CacheProxyVideoDownloader implements VideoDownloader {
    protected RealVideoDownloader realVideoDownload;
    Map<String, String> cache;

    public CacheProxyVideoDownloader(){
        this.realVideoDownload = new RealVideoDownloader();
        this.cache = new HashMap<>();
    }

    @Override 
    public String downloadVideo(String url){
        if(cache.containsKey(url)){
            System.out.println("Returning cached video for url : "+url);
            return cache.get(url);
        }

        System.out.println("Cache missed Downloading Video");
        String videoContent = realVideoDownload.downloadVideo(url);
        cache.put(url, videoContent);

        return videoContent;
    }
}

//------------------------------------------------------------------------------------------------
// FACADE PATTERN -- Provides the unified interface for complex system or group of classes
//------------------------------------------------------------------------------------------------

class PaymentService {
    public void makePayment(String accountId, double amount) {
        System.out.println("Payment of ₹" + amount + " successful for account " + accountId);
    }
}

class SeatReservationService {
    public void reserveSeat(String movieId, String seatNumber) {
        System.out.println("Seat " + seatNumber + " reserved for movie " + movieId);
    }
}

class NotificationService {
    public void sendBookingConfirmation(String userEmail) {
        System.out.println("Booking confirmation sent to " + userEmail);
    }
}

class LoyaltyPointsService {
    public void addPoints(String accountId, int points) {
        System.out.println(points + " loyalty points added to account " + accountId);
    }
}

class TicketService {
    public void generateTicket(String movieId, String seatNumber) {
        System.out.println("Ticket generated for movie " + movieId + ", Seat: " + seatNumber);
    }
}

// ========== The MovieBookingFacade class  ==============
class MovieBookingFacade {
    private PaymentService paymentService;
    private SeatReservationService seatReservationService;
    private NotificationService notificationService;
    private LoyaltyPointsService loyaltyPointsService;
    private TicketService ticketService;

    public MovieBookingFacade() {
        this.paymentService = new PaymentService();
        this.seatReservationService = new SeatReservationService();
        this.notificationService = new NotificationService();
        this.loyaltyPointsService = new LoyaltyPointsService();
        this.ticketService = new TicketService();
    }
    
    public void bookMovieTicket(String accountId, String movieId, String seatNumber, String userEmail, double amount) {
        paymentService.makePayment(accountId, amount);
        seatReservationService.reserveSeat(movieId, seatNumber);
        ticketService.generateTicket(movieId, seatNumber);
        loyaltyPointsService.addPoints(accountId, 50);
        notificationService.sendBookingConfirmation(userEmail);

        System.out.println("Movie ticket booking completed successfully!");
    }
}
// Client Code
class BookMyShow {
    public static void main(String[] args) {
        // Booking a movie ticket manually (using facade)
        MovieBookingFacade movieBookingFacade = new MovieBookingFacade();
        movieBookingFacade.bookMovieTicket("user123", "movie456", "A10", "user@example.com", 500);
    }
}

//------------------------------------------------
// FLY-WEIGHT 
//------------------------------------------------

// Intrinsic (Non mutable)
class TreeType {
    String name, color, texture;

    public TreeType(String name, String color, String texture){
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public void display(int x, int y){
        System.out.println("Tree placed at ("+x+", "+y+") - ("+name+","+color+","+texture+")");
    }
}

// Extrinsic (Mutable)
class Tree {
    int x;
    int y;
    TreeType treeType;

    public Tree(int x, int y, TreeType treeType){
        this.x = x;
        this.y = y;
        this.treeType = treeType;
    }

    public void draw(){
        treeType.display(x, y);
    }
}

class TreeTypeFactory {
    static private Map<String, TreeType> treeTypeMap = new HashMap<>();

    static TreeType getTreeType(String name, String color, String texture){
        String key = name+"-"+color+"-"+texture;

        if(!treeTypeMap.containsKey(key)){
            treeTypeMap.put(key, new TreeType(name, color, texture));
        }

        return treeTypeMap.get(key);
    }
}

class Forest {
    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture){
        Tree tree = new Tree(x, y, TreeTypeFactory.getTreeType(name, color, texture));
        trees.add(tree);
    }

    public void draw(){
        for(Tree tree : trees){
            tree.draw();
        }
    }
}

class GoogleMap {
    public static void main(String[] args) {
        Forest forest = new Forest();
        
        // Planting 1 million trees
        for(int i = 0; i < 1000000; i++) {
            forest.plantTree(i, i, "Oak", "Green", "Rough");
        }
        
        System.out.println("Planted 1 million trees.");
    }
}


//------------------------------------------------
// STRUCTURAL PATTERN -- END
//------------------------------------------------


//------------------------------------------------
// BEHAVIOURAL PATTERNS -- START (CC-II-MM-OSS-TV)
//------------------------------------------------

//------------------------------------------------
// ITERATOR PATTERN -- 
//------------------------------------------------

class Video {
    private String title;

    public Video(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }
}

interface Playlist {
    PlaylistIterator createIterator();
}

class YoutubePlaylist implements Playlist {
    private List<Video> playlist;

    public void addVideo(Video video){
        playlist.add(video);
    }

    @Override 
    public PlaylistIterator createIterator(){
        return(new YoutubePlaylistIterator(Collections.unmodifiableList(playlist)));
    }
}

interface PlaylistIterator {
    boolean hasNext();
    Video next();
}

class YoutubePlaylistIterator implements PlaylistIterator {
    private List<Video> youtubePlaylist;
    private int position;
    
    public YoutubePlaylistIterator(List<Video> youtubePlaylist){
        this.youtubePlaylist = youtubePlaylist;
        this.position = 0;
    }

    @Override 
    public boolean hasNext(){
        return  position < youtubePlaylist.size();
    }

    @Override 
    public Video next(){
        return hasNext() ? youtubePlaylist.get(position++) : null;
    }
}

class Youtube {
    public static void main(String args[]){
        YoutubePlaylist cooking = new YoutubePlaylist();
        cooking.addVideo(new Video("Paneer Tikka Recipe"));
        cooking.addVideo(new Video("Lasagna Recipe"));

        PlaylistIterator iterator = cooking.createIterator();

        while(iterator.hasNext()){
            System.out.println(iterator.next().getTitle());
        }
    }
}


//------------------------------------------------
// OBSERVER PATTERN -- NOTIFICATOIN HANDLING 
//------------------------------------------------

interface Subscriber {
    void notify(String videoTitle);    
}

class EmailSubscriber implements Subscriber {
    private String email;
    public EmailSubscriber(String email){
        this.email = email;
    }

    @Override 
    public  void notify(String videoTitle){
        System.out.println("Notification To : "+email+" Video Update : "+videoTitle);
    }
}
class MobileSubscriber implements Subscriber {
    private String userName;
    public MobileSubscriber(String userName){
        this.userName = userName;
    }

    @Override 
    public  void notify(String videoTitle){
        System.out.println("Notifying User : "+userName+" Video Update : "+videoTitle);
    }
}


interface Channel {
    void NotifySub(String title);
    void Subscribe(Subscriber sub);
    void UnSubscribe(Subscriber sub);
}

class YoutubeChannel implements Channel {
    private List<Subscriber> channelSub;
    String channelName;

    public YoutubeChannel(String channelName){
        this.channelName = channelName;
        this.channelSub = new ArrayList<>();
    }

    @Override 
    public void Subscribe(Subscriber sub){
        channelSub.add(sub);
    }

    @Override 
    public void UnSubscribe(Subscriber sub){
        channelSub.remove(sub);
    }

    @Override 
    public void NotifySub(String title){
        for(Subscriber  sub : channelSub){
            sub.notify(title);
        }
    }   

    public void uploadVideo(String title){
        System.out.println(channelName+" Uploaded "+title+" Video.");
        NotifySub(title);
    }
}

class YoutubeNotifier {
    public static void main(String[] args) {
        YoutubeChannel tuf = new YoutubeChannel("takeUforward");
 
        // Add subscribers
        tuf.Subscribe(new MobileSubscriber("raj"));
        tuf.Subscribe(new EmailSubscriber("rahul@example.com"));
 
        // Upload video and notify all observers
        tuf.uploadVideo("observer-pattern");
    }
}


//-------------------------------------------------------------------------
// STATEGY PATTERN -- DYNAMIC BEHAVIOUR OF DECIDING ALGORITHMS(STRATEGIES) 
//-------------------------------------------------------------------------

interface Strategies {
    void mathc(String location);
}

class NearestDriverStrategy implements Strategies {
    @Override 
    public void mathc(String location){
        System.out.println("Assigning Near by Driver Strategy for the location "+location);
    }
}
class AirportQueueStrategy implements Strategies {
    @Override 
    public void mathc(String location){
        System.out.println("Assigning Air Port Queue Driver Strategy for the location "+location);
    }
}

class RideMatchingService {
    private Strategies strategy;

    public RideMatchingService(Strategies strategy){
        this.strategy = strategy;
    }

    public void setStrategy(Strategies strategy){
        this.strategy = strategy;
    }

    public void matchRider(String location){
        strategy.mathc(location);
    }
}

class Uber {
    public static void main(String[] args) {
        // Using airport queue strategy
        RideMatchingService rideMatchingService = new RideMatchingService(new AirportQueueStrategy());
        rideMatchingService.matchRider("Terminal 1");
 
        // Using nearest driver strategy and later switching to surge priority
        RideMatchingService rideMatchingService2 = new RideMatchingService(new NearestDriverStrategy());
        rideMatchingService2.matchRider("Downtown");
        rideMatchingService2.setStrategy(new AirportQueueStrategy());
        rideMatchingService2.matchRider("Downtown");
    }
}


//------------------------------------------------
// COMMAND PATTERN  
//------------------------------------------------

class Ac {
    public void on(){
        System.out.println("Ac Turned On");
    }
    public void off(){
        System.out.println("Ac Turned Off");
    }
}

class Light {
    public void on(){
        System.out.println("Light Turned On");
    }
    public void off(){
        System.out.println("Light Turned Off");
    }
}

interface Commands {
    void execute();
    void undo();
}

class AcTurnOn implements Commands {
    private Ac ac;
    public AcTurnOn(Ac ac){
        this.ac = ac;
    }

    @Override 
    public void execute(){
        ac.on();
    }

    @Override 
    public void undo(){
        ac.off();
    }
}
class AcTurnOff implements Commands {
    private Ac ac;
    public AcTurnOff(Ac ac){
        this.ac = ac;
    }

    @Override 
    public void execute(){
        ac.off();
    }

    @Override 
    public void undo(){
        ac.on();
    }
}
class LightTurnOn implements Commands {
    private Light light;
    public LightTurnOn(Light light){
        this.light = light;
    }

    @Override 
    public void execute(){
        light.on();
    }

    @Override 
    public void undo(){
        light.off();
    }
}
class LightTurnOff implements Commands {
    private Light light;
    public LightTurnOff(Light light){
        this.light = light;
    }

    @Override 
    public void execute(){
        light.off();
    }

    @Override 
    public void undo(){
        light.on();
    }
}

class RemoteControl {
    private Commands[] remote = new Commands[4];
    private Stack<Commands> commandHistory = new Stack<>();

    public void setCommands(int slot, Commands command){
        remote[slot] = command;
    }

    public void pressButton(int slot){
        if(remote[slot]!=null){
            remote[slot].execute();
            commandHistory.push(remote[slot]);
        }
        else{
            System.out.println("No Command Set on slot : "+slot);
        }
    }

    public void pressUndo(){
        if(!commandHistory.isEmpty()){
            commandHistory.pop().undo();
        }
        else{
            System.out.println("Nothing to Undo!");
        }
    }
}
class RemoteController {
    public static void main(String[] args) {
        Light light = new Light();
        Ac ac = new Ac();

        Commands lightOn = new LightTurnOn(light);
        Commands lightOff = new LightTurnOff(light);
        Commands acOn = new AcTurnOn(ac);
        Commands acOff = new AcTurnOff(ac);

        RemoteControl remote = new RemoteControl();
        remote.setCommands(0, lightOn);
        remote.setCommands(1, lightOff);
        remote.setCommands(2, acOn);
        remote.setCommands(3, acOff);

        remote.pressButton(0); // Light ON
        remote.pressButton(2); // AC ON
        remote.pressButton(1); // Light OFF
        remote.pressUndo();    // Undo Light OFF -> Light ON
        remote.pressUndo();    // Undo AC ON -> AC OFF
    }
}


//------------------------------------------------
// TEMPLATE METHOD 
// Template Class (Skeleton) --> Concrete Mehod (Common to all Subclass)
//                           --> Primitive (Abstract Method) implemented by subclass
//                           --> Hooks (Optional Method) Override by Sub Class 
//------------------------------------------------

abstract class NotificationSender {
    // Skeleton
    public final void send(String to, String rawMessage){
        // Common Methods to all sub classes
        validateRecipient(to);
        String formatedMsg = formatMsg(rawMessage);
        preSendAuditLog(to, formatedMsg);

        // Implemented by the sub class
        String composedMsg = composeMessage(formatedMsg);
        sendMessage(to, composedMsg);

        // Hook (Optional Methods)
        postSendAnalysis(to, composedMsg);
    }

    public void validateRecipient(String to){
        System.out.println("Validating : "+to);
    }

    public String formatMsg(String rawMsg){
        return rawMsg.trim();
    }

    public void preSendAuditLog(String to, String formatedMsg){
        System.out.println("Loggin Pre Send Msg : "+formatedMsg+" Send To : "+to);
    }

    abstract public String composeMessage(String formatedMsg);
    abstract public void sendMessage(String to, String composedMsg);

    // Hook
    public void postSendAnalysis(String to, String composedMsg){
        System.out.println("Default Post Analysis For Msg : "+composedMsg+ " Send TO : "+to);
    }
} 


class EmailNotification extends NotificationSender {
    @Override 
    public String composeMessage(String formatedMsg){
        return "<html>"+formatedMsg+"</html>";
    }

    @Override 
    public void sendMessage(String to, String composedMsg){
        System.out.println("Email To : "+to+" Body : "+composedMsg);
    }
}
class WhatsappNotification extends NotificationSender {
    @Override 
    public String composeMessage(String formatedMsg){
        return "Message : "+formatedMsg;
    }

    @Override 
    public void sendMessage(String to, String composedMsg){
        System.out.println("Whatsapp To : "+to+" Message : "+composedMsg);
    }
}

class Notification {
    public static void main(String[] args) {
        // Create objects for both notification services
        EmailNotification emailNotification = new EmailNotification();
        WhatsappNotification smsNotification = new WhatsappNotification();
 
        // Sending email notification
        emailNotification.send("example@example.com", "Your order has been placed!");
        
        System.out.println(" ");
        
        // Sending SMS notification
        smsNotification.send("1234567890", "Your OTP is 1234.");
    }
}



//------------------------------------------------
// STATE PATTERN
//------------------------------------------------


class OrderContext {
    private OrderState currentOrderState;
    public OrderContext(){
        currentOrderState = new OrderPlaceState();
    } 

    public void setState(OrderState state){
        this.currentOrderState = state;
    }

    public void nextState(){
        currentOrderState.next(this);
    }

    public void cancel(){
        currentOrderState.cancelOrder(this);
    }

    public String getCurrOrderState(){
        return currentOrderState.getState();
    }
}

interface OrderState {
    void next(OrderContext context);
    void cancelOrder(OrderContext context);
    String getState();
}


class OrderPlaceState implements OrderState {
    @Override 
    public void next(OrderContext context){
        context.setState(new OrderPreparingState());
    }

    @Override 
    public void cancelOrder(OrderContext context){
        context.setState(new CancelOrder());
    }

    @Override 
    public String getState(){
        return "ORDER_PLACED";
    }
}
class OrderPreparingState implements OrderState {
    @Override 
    public void next(OrderContext context){
        context.setState(new OutOfDelivery());
    }

    @Override 
    public void cancelOrder(OrderContext context){
        context.setState(new CancelOrder());
    }

    @Override 
    public String getState(){
        return "ORDER_PREPARING";
    }
}
class OutOfDelivery implements OrderState {
    @Override 
    public void next(OrderContext context){
        context.setState(new OrderDelivered());
    }

    @Override 
    public void cancelOrder(OrderContext context){
        System.out.println("Order out for Delviery. Cancellation not possible!");
    }

    @Override 
    public String getState(){
        return "OUT_FOR_DELIVERY";
    }
}
class OrderDelivered implements OrderState {
    @Override 
    public void next(OrderContext context){
        System.out.println("Order is Already Delivered!");
    }

    @Override 
    public void cancelOrder(OrderContext context){
        System.out.println("Order Delivered. Cancellation not possible!");
    }

    @Override 
    public String getState(){
        return "ORDER_DELIVERED";
    }
}
class CancelOrder implements OrderState {
    @Override 
    public void next(OrderContext context){
        System.out.println("Order is Already Cancelled!");
    }

    @Override 
    public void cancelOrder(OrderContext context){
        System.out.println("Order is Already Cancelled. Not possible to Cancelled!");
    }

    @Override 
    public String getState(){
        return "CANCEL_ORDER";
    }
}


class Zomato {
    public static void main(String[] args) {
        OrderContext order = new OrderContext();

        // Display initial state
        System.out.println("Current State: " + order.getCurrOrderState());

        // Moving through states
        order.nextState();  // ORDER_PLACED -> PREPARING
        order.nextState();  // PREPARING -> OUT_FOR_DELIVERY
        order.cancel(); // Should fail, as order is out for delivery
        order.nextState();  // OUT_FOR_DELIVERY -> DELIVERED
        order.cancel(); // Should fail, as order is delivered

        // Display final state
        System.out.println("Final State: " + order.getCurrOrderState());
    }
}



//--------------------------------------------------
// CHAIN OF RESPONSIBILITY -- Customer Care Example
//--------------------------------------------------

abstract class SupportHandler {
    private SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    public void handleRequest(String request){
        if(canHandle(request)){
            resolveReq(request);
        }
        else if(nextHandler != null){
            nextHandler.handleRequest(request);
        }
        else{
            System.out.println("No handler found for the request : "+request);
        }
    }

    protected abstract boolean canHandle(String request);
    protected abstract void resolveReq(String request);
}

class GeneralHandler extends SupportHandler {
    @Override 
    public boolean canHandle(String request){
        return request.equalsIgnoreCase("GENERAL");
    }

    @Override 
    public void resolveReq(String request){
        System.out.println("Request : "+request+" Getting Resolved by General Handler");
    }
}

class TechnicalHandler extends SupportHandler {
    @Override 
    public boolean canHandle(String request){
        return request.equalsIgnoreCase("TECHNICAL");
    }

    @Override 
    public void resolveReq(String request){
        System.out.println("Request : "+request+" Getting Resolved by Technical Handler");
    }
}

class DeliveryHandler extends SupportHandler {
    @Override 
    public boolean canHandle(String request){
        return request.equalsIgnoreCase("DELIVERY");
    }

    @Override 
    public void resolveReq(String request){
        System.out.println("Request : "+request+" Getting Resolved by Delivery Handler");
    }
}


class CustomerCare {
    public static void main(String args[]){
        SupportHandler generalHandler = new GeneralHandler();
        SupportHandler techHandler = new TechnicalHandler();
        SupportHandler deliverHandler = new DeliveryHandler();

        generalHandler.setNextHandler(techHandler);
        techHandler.setNextHandler(deliverHandler);

        generalHandler.handleRequest("delivery");
    }
}


//--------------------------------------------------------------------------------
// VISITOR PATTERN -- Similar kind of operation to be applied on multiple classes
//--------------------------------------------------------------------------------

interface ProductType {
    void accept(Visitor operation);
}

class PhysicalProduct implements ProductType {
    String name;
    double cost;
    public PhysicalProduct(String name, double cost){
        this.name = name;
        this.cost = cost;
    }

    @Override 
    public void accept(Visitor operation){
        operation.visit(this);
    }
}
class DigitalProduct implements ProductType {
    String name;
    double downloadSize;
    public DigitalProduct(String name, double downloadSize){
        this.name = name;
        this.downloadSize = downloadSize;
    }

    @Override 
    public void accept(Visitor operation){
        operation.visit(this);
    }
}
class GiftCard implements ProductType {
    String code;
    double amount;
    public GiftCard(String code, double amount){
        this.code = code;
        this.amount = amount;
    }

    @Override 
    public void accept(Visitor operation){
        operation.visit(this);
    }
}

interface Visitor {
    void visit(PhysicalProduct physicalProduct);
    void visit(DigitalProduct digitalProduct);
    void visit(GiftCard giftCard);
}

class InvoiceVisitor implements Visitor {
    @Override 
    public void visit(PhysicalProduct physicalProduct){
        System.out.println("Invoice : "+physicalProduct.name+" for Physical Product!");
    }
    @Override 
    public void visit(DigitalProduct digitalProduct){
        System.out.println("Invoice : "+digitalProduct.name+" for Digital Product!");
    }
    @Override 
    public void visit(GiftCard giftCard){
        System.out.println("Invoice : "+giftCard.code+" for Gift Card!");
    }
}

class ShippingCostVisitor implements Visitor {
    @Override 
    public void visit(PhysicalProduct physicalProduct){
        System.out.println("Shipping Cost - ");
    }
    @Override 
    public void visit(DigitalProduct digitalProduct){
        System.out.println("Invoice : "+digitalProduct.name+" for Digital Product!");
    }
    @Override 
    public void visit(GiftCard giftCard){
        System.out.println("Invoice : "+giftCard.code+" for Gift Card!");
    }
}

class AmazonOperationHandler {
    public static void main(String args[]){
        List<ProductType> cart = new ArrayList<>();

        cart.add(new PhysicalProduct("Cycle", 27000));
        cart.add(new DigitalProduct("E-book", 1000));
        cart.add(new GiftCard("HELLO123", 500));

        // Operation to perform
        Visitor invoiceVisitor = new InvoiceVisitor();
        Visitor shipVisitor = new ShippingCostVisitor();

        for(ProductType item : cart){
            item.accept(invoiceVisitor);
            item.accept(shipVisitor);
        }
    }
}



//--------------------------------------------------
// MEDIATOR PATTERN -- ATC IN CASE OF THE AIRLINE
//--------------------------------------------------

// 1. Define Roles
enum Role {
    VIEWER,  // Can only read
    EDITOR,  // Can read and edit
    ADMIN    // Can read, edit, and delete
}

// 2. Expanded Mediator Interface
interface DocumentSessionMediator {
    void join(User user);
    void broadcastChange(String change, User sender);
    void deleteDocument(User sender);
}

// 3. User Class with Roles
class User {
    protected String name;
    protected Role role;
    protected DocumentSessionMediator mediator;

    public User(String name, Role role, DocumentSessionMediator mediator) {
        this.name = name;
        this.role = role;
        this.mediator = mediator;
    }

    public Role getRole() {
        return role;
    }

    // Actions the user attempts to perform
    public void makeChange(String change) {
        System.out.println(name + " attempting to edit...");
        mediator.broadcastChange(change, this);
    }

    public void attemptDelete() {
        System.out.println(name + " attempting to delete document...");
        mediator.deleteDocument(this);
    }

    public void receiveChange(String change, User sender) {
        System.out.println(name + " saw change from " + sender.name + ": \"" + change + "\"");
    }
}

// 4. The Role-Based Mediator
class RoleBasedDocumentSession implements DocumentSessionMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void join(User user) {
        users.add(user);
        System.out.println(user.name + " joined as " + user.getRole());
    }

    @Override
    public void broadcastChange(String change, User sender) {
        // PERMISSION CHECK: Only EDITORS and ADMINS can make changes
        if (sender.getRole() == Role.VIEWER) {
            System.out.println("[ACCESS DENIED] " + sender.name + " cannot edit. Viewers are read-only.\n");
            return; 
        }

        System.out.println("[SUCCESS] Edit accepted.");
        for (User user : users) {
            if (user != sender) {
                user.receiveChange(change, sender);
            }
        }
        System.out.println();
    }

    @Override
    public void deleteDocument(User sender) {
        // PERMISSION CHECK: Only ADMINS can delete the document
        if (sender.getRole() != Role.ADMIN) {
            System.out.println("[ACCESS DENIED] " + sender.name + " cannot delete. Admin privileges required.\n");
            return;
        }

        System.out.println("[SUCCESS] Document deleted by " + sender.name + ".\n");
        // Logic to close session and boot users would go here
    }
}

// 5. Client Code
class WordDocs {
    public static void main(String[] args) {
        DocumentSessionMediator rbacDoc = new RoleBasedDocumentSession();

        // Create users with different roles
        User adminUser = new User("Alice", Role.ADMIN, rbacDoc);
        User editorUser = new User("Bob", Role.EDITOR, rbacDoc);
        User viewerUser = new User("Charlie", Role.VIEWER, rbacDoc);

        rbacDoc.join(adminUser);
        rbacDoc.join(editorUser);
        rbacDoc.join(viewerUser);
        System.out.println("-------------------------------------------------");

        // Charlie (Viewer) tries to edit
        viewerUser.makeChange("I think we should add a paragraph here.");

        // Bob (Editor) tries to edit
        editorUser.makeChange("Fixed the spelling errors.");

        // Bob (Editor) tries to delete the document
        editorUser.attemptDelete();

        // Alice (Admin) deletes the document
        adminUser.attemptDelete();
    }
}




public  class Practice{
    public static void main(String args[]){
        // Scanner sc= new Scanner(System.in);

    }
}