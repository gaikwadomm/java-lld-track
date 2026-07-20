class OuterClass{
    static int val = 10;
    String name = "om";

    class InnerClass{
        public void execute(){
            System.out.println("Inner Class Executed"+name+" val "+val);
            // System.out.println("Inner Class Executed"+name); // Will through error as name is non static
        }
    }
}

public class NonStatic {
    public static void main(String[] args){

        OuterClass.InnerClass innerClassInst = new OuterClass().new InnerClass();

        // OR

        // OuterClass outerClassInst = new OuterClass();
        
        // OuterClass.InnerClass innerClassInst = outerClassInst.new InnerClass();


       
    }
}
