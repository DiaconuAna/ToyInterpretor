package Model.ADTs;
import java.util.Stack;
import Exceptions.ADTsExceptions;

public class MyStack<T> implements StackInterface<T> {

    private Stack<T> stack;

    public MyStack(){
        this.stack = new Stack<>();
    }

    @Override
    public T pop() throws ADTsExceptions{
        if(this.stack.isEmpty())
            throw new ADTsExceptions("Cannot pop from an empty stack!");
        return this.stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString(){
        return this.stack.toString();
    }
}
