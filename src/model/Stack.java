package model;

import java.util.ArrayList;
import java.util.List;

/** Generic LIFO stack. */
public class Stack<T> {
    private final List<T> items = new ArrayList<>();
    public void push(T value) { items.add(value); }
    public T pop() { return isEmpty() ? null : items.remove(items.size() - 1); }
    public T peek() { return isEmpty() ? null : items.get(items.size() - 1); }
    public boolean isEmpty() { return items.isEmpty(); }
    public int size() { return items.size(); }
    public void clear() { items.clear(); }
}
