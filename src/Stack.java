import java.util.ArrayList;

public class Stack {

    private ArrayList<String> stack;

    public Stack() {
        stack = new ArrayList<>();
    }

    // Push element
    public void push(String value) {
        stack.add(value);
    }

    // Pop element
    public String pop() {
        if (isEmpty()) {
            return null;
        }
        return stack.remove(stack.size() - 1);
    }

    // Peek top element
    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    // Check empty
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Size of stack
    public int size() {
        return stack.size();
    }

    // Clear stack
    public void clear() {
        stack.clear();
    }
}