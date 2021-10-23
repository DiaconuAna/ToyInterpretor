package Exceptions;

public class RepositoryException extends Exception {
    public RepositoryException(){}
    public RepositoryException(String msg){
        super(msg);
    }

}