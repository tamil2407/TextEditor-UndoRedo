
package model;

import java.util.ArrayList;

public class Stack {
    private final ArrayList<String> items = new ArrayList<>();

    public void push(String value) {
        items.add(value);
    }

    public String pop() {
        if (isEmpty()) {
            return null;
        }
        return items.remove(items.size() - 1);
    }

    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return items.get(items.size() - 1);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
