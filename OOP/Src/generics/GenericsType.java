package Src.generics;

import java.util.ArrayList;
import java.util.List;

class GenericExample<T> {
    List<T> arr;

    GenericExample() {
        this.arr = new ArrayList<>();
    }
    
    public void add(T val){
        arr.add(val);
    }

    public void removeLast(){
        if(arr.isEmpty()){
            System.out.println("Empty List!");
            return;
        }

        arr.remove(arr.size()-1);
    }

    public void printVal(){
        for(T val: arr){
            System.out.println(val+" ");
        }
    }
}

public class GenericsType {
    public static void main(String[] args){
        GenericExample<Integer> obj = new GenericExample<>();

        obj.add(1);
        obj.add(2);
        obj.add(3);
        obj.removeLast();

        obj.printVal();
    }
}
