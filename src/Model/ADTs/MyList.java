package Model.ADTs;
import Exceptions.ADTsExceptions;

import java.util.LinkedList;

public class MyList<T> implements ListInterface<T> {

    LinkedList<T> list;

    public MyList(){
        this.list = new LinkedList<T>();
    }

    @Override
    public void add(T v) {
        this.list.add(v);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    public T getFirst() throws ADTsExceptions{
        if(this.list.size() == 0)
            throw new ADTsExceptions("List is empty.");
        return this.list.getFirst();
    }

    public T getByIndex(int index) throws ADTsExceptions {
        if(index >= this.list.size())
            throw new ADTsExceptions("Invalid index.");
        return this.list.get(index);
    }

    @Override
    public String toString(){
        return null;
    }
}
