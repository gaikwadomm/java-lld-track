package Src.inner_class_types;

abstract class GreetingHello {
    abstract void sayHello();
}

class Annonymousinner {
    public static void main(String[] args) {
        // Anonymous inner class
        GreetingHello greeting = new GreetingHello() { 
            void sayHello() {
                System.out.println("Hello, World!");
            }
        };

        greeting.sayHello(); // Output: Hello, World!
    }
}