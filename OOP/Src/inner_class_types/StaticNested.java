package InnerClassTypes;

class OuterClass{
    static int val = 10;
    String name = "om";

    static class InnerClass{
        public void execute(){
            System.out.println("Inner Class Executed"+val);
            // System.out.println("Inner Class Executed"+name); // Will through error as name is non static
        }
    }
}

public class StaticNested {
    public static void main(String[] args){
        OuterClass.InnerClass executeInnerClass = new OuterClass.InnerClass();

        executeInnerClass.execute();
       
    }
}
