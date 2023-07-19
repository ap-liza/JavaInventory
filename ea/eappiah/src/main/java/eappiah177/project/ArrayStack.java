package eappiah177.project;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ArrayStack<T> implements StackADT<T>{
    private  Queue<T> q;
    private List<T> list;
    Stack<T> stack1;
    private int total = 0;
    private ArrayList<T> stack;

    @Override
    public void push(T element) {
        // Increase the total by one since we are pushing a new item
        total = stack.size()+1;
        // insert the element
        stack.add(element);
    }

    /**
     * Removes the element at the top of this stack and returns a
     * reference to it.
     * @return element removed from top of stack
     * @throws EmptyCollectionException if stack is empty
     */
    @Override
    public T pop() throws EmptyCollectionException{
        if(isEmpty())
            throw  new EmptyCollectionException("stack");
        total--;
        T result = stack.get(total);
        stack.remove(total);
        return result;
    }

    @Override
    public T peek() throws EmptyCollectionException{
        if (isEmpty())
            throw new EmptyCollectionException("stack");
        return stack.get(total-1);
    }

    @Override
    public boolean isEmpty() {
        return stack.size()==0;
    }

    @Override
    public int size() {
        return stack.size();
    }
}
