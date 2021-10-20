package Model.ADTs;
import Exceptions.ADTsExceptions;

public interface StackInterface<T> {
    T pop() throws ADTsExceptions;
    public void push(T v);
    boolean isEmpty();
    String toString();
}
