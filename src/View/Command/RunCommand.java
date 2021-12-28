package View.Command;

import Controller.Controller;

public class RunCommand extends Command{
    private Controller controller;

    public RunCommand(String key, String desc, Controller c){
        super(key, desc);
        this.controller = c;
    }

    @Override
    public void execute(){
        try{
            this.controller.typecheck();
            this.controller.allStep();
        }
        catch (Exception exp){
            System.out.println(exp);
        }
    }
}
