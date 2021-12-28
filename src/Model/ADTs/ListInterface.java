package Model.ADTs;
import Exceptions.ADTsExceptions;


public interface ListInterface<T> {
    void add(T v);
    String toString();
    boolean isEmpty();
    void clear();
    public ListInterface<T> clone();

}
