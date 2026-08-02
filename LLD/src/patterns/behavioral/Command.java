package patterns.behavioral;

import java.util.ArrayList;
import java.util.Stack;

// Client Has Invoker (Remote)
// Through Invoker we can execute Commands
// Command Execution will trigger the Reciver (Ac or Light)

class Ac {
    public void turnOn(){
        System.out.println("Ac get Turn ON!");
    }

    public void turnOff(){
        System.out.println("Ac get Turn OFF!");
    }
}

class Light {
    public void turnOn(){
        System.out.println("Light get Turn ON!");
    }

    public void turnOff(){
        System.out.println("Light get Turn OFF!");
    }
}

interface Commands {
    void execute();
    void undo();
}

class TurnOnAc implements Commands {
    private Ac ac;
    TurnOnAc(Ac ac){
        this.ac = ac;
    }

    @Override
    public void execute(){
        ac.turnOn();
    }

    @Override
    public void undo(){
        ac.turnOff();
    }
}

class TurnOffAc implements Commands {
    private Ac ac;
    TurnOffAc(Ac ac){
        this.ac = ac;
    }

    @Override
    public void execute(){
        ac.turnOff();
    }

    @Override
    public void undo(){
        ac.turnOn();
    }
}

class TurnOnLight implements Commands {
    private Light light;
    TurnOnLight(Light light){
        this.light = light;
    }

    @Override
    public void execute(){
        light.turnOn();
    }

    @Override
    public void undo(){
        light.turnOff();
    }
}

class TurnOffLight implements Commands {
    private Light light;
    TurnOffLight(Light light){
        this.light = light;
    }

    @Override
    public void execute(){
        light.turnOff();
    }

    @Override
    public void undo(){
        light.turnOn();
    }
}

// Revoker (Remote)

class RemoteControl {

    private Commands[] buttons = new Commands[4];
    Stack<Commands> commandsHistory = new Stack<>();

    public void addButton(int idx, Commands btn){
        buttons[idx] = btn;
    }

    public void pressButton(int slot){
        if(buttons[slot]!=null){
            buttons[slot].execute();
            commandsHistory.push(buttons[slot]);
        }
        else{
            System.out.println("Button not allocated!");
        }
    }

    public void pressUndo(){
        if(!commandsHistory.isEmpty()){
            commandsHistory.pop().undo();
        }
        else{
            System.out.println("Nothing to Undo!");
        }
    }
}

public class Command {
    public static void main(String[] args) {
        Ac ac = new Ac();
        Light light = new Light();

        RemoteControl remote = new RemoteControl();

        TurnOnAc onAc = new TurnOnAc(ac);
        TurnOffAc offAc = new TurnOffAc(ac);
        TurnOnLight onLight = new TurnOnLight(light);
        TurnOffLight offLight = new TurnOffLight(light);

        remote.addButton(0, offAc);
        remote.addButton(1, offLight);
        remote.addButton(2, onAc);
        remote.addButton(3, onLight);

        remote.pressButton(0);
        remote.pressButton(2);
        remote.pressButton(3);
        remote.pressButton(1);
        remote.pressButton(3);

        remote.pressUndo();

    }
}
