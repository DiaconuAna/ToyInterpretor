package Exceptions;

public class ControllerException extends Exception {
    public ControllerException(){}
    public ControllerException(String msg){
        super(msg);
    }

}