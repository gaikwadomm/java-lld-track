package patterns.creational;
import java.util.*;

interface EmailTemplate extends Cloneable{
    EmailTemplate clone();
    void setContent(String content);
    void send(String to);
}

class WelcomeEmail implements EmailTemplate {
    private String subject;
    private String content;

    public WelcomeEmail(){
        this.subject = "Welcome to Jane Street!";
        this.content = "Hi there! Let dive into the world of Competative Programming!";
    }

    @Override
    public WelcomeEmail clone(){
        try {
            return (WelcomeEmail) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone Failed", e);
        }
    }

    @Override
    public void setContent(String content){
        this.content = content;
    }

    @Override
    public void send(String to){
        System.out.println("Email Send to "+to+". Subject : "+ subject +". Message : "+content);
    }
}

class EmailTemplateRegistry {
    private static final Map<String, EmailTemplate> templates = new HashMap<>();

    // Don't wait for the Run Time 
    // Just do the put on the compilation time
    static {
        templates.put("welcome", new WelcomeEmail());
        // templates.put("discount", new DiscountEmail());
        // templates.put("feature-update", new FeatureUpdateEmail());
    }

    public static EmailTemplate getTemplate(String type) {
        return templates.get(type).clone(); // clone to avoid modifying original
    }
}

public class Prototype {
    public static void main(String[] args) {
        EmailTemplate welcomeEmail1 = EmailTemplateRegistry.getTemplate("welcome");

        welcomeEmail1.setContent("Hi Alice, welcome to Jane Street Premium!");
        welcomeEmail1.send("alice@example.com");

        EmailTemplate welcomeEmail2 = EmailTemplateRegistry.getTemplate("welcome");
        welcomeEmail2.setContent("Hi Bob, thanks for joining!");
        welcomeEmail2.send("bob@example.com");
    }
}
