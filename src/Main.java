import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TextEditor editor = new TextEditor();

        int choice;

        do {

            System.out.println("\n==============================");
            System.out.println(" TEXT EDITOR WITH UNDO/REDO");
            System.out.println("==============================");
            System.out.println("1. Type Text");
            System.out.println("2. Undo");
            System.out.println("3. Redo");
            System.out.println("4. Show Current Text");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter text: ");
                    String text = scanner.nextLine();
                    editor.typeText(text);
                    break;

                case 2:
                    editor.undo();
                    break;

                case 3:
                    editor.redo();
                    break;

                case 4:
                    editor.showText();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        scanner.close();
    }
}

class Stack {

    private ArrayList<String> stack = new ArrayList<>();

    public void push(String value) {
        stack.add(value);
    }

    public String pop() {
        if (isEmpty()) {
            return null;
        }
        return stack.remove(stack.size() - 1);
    }

    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
    }
}

class UndoRedoManager {

    private Stack undoStack = new Stack();
    private Stack redoStack = new Stack();

    public void saveState(String currentText) {
        undoStack.push(currentText);
        redoStack.clear();
    }

    public String undo(String currentText) {

        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return currentText;
        }

        redoStack.push(currentText);
        return undoStack.pop();
    }

    public String redo(String currentText) {

        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return currentText;
        }

        undoStack.push(currentText);
        return redoStack.pop();
    }
}

class TextEditor {

    private String currentText = "";
    private UndoRedoManager manager = new UndoRedoManager();

    public void typeText(String text) {
        manager.saveState(currentText);
        currentText += text;
    }

    public void undo() {
        currentText = manager.undo(currentText);
    }

    public void redo() {
        currentText = manager.redo(currentText);
    }

    public void showText() {
        System.out.println("\nCurrent Text: " + currentText);
    }
}