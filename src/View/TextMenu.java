package View;

import Controller.Controller;
import View.Command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu(){
        commands = new HashMap<>();
    }

    public void addCommand(Command c){
        commands.put(c.getKey(), c);
    }

    private void PrintMenu(){
        for(Command c: commands.values()){
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            PrintMenu();
            System.out.printf("Input the option: ");
            String key = scanner.nextLine();
            Command c = commands.get(key);

            if(c == null){
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            c.execute();
        }
    }

}
