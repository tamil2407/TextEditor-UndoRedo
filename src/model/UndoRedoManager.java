
package model;

public class UndoRedoManager {
    private final Stack undoStack = new Stack();
    private final Stack redoStack = new Stack();

    public void saveState(String currentText) {
        undoStack.push(currentText);
        redoStack.clear();
    }

    public String undo(String currentText) {
        if (undoStack.isEmpty()) {
            return currentText;
        }

        redoStack.push(currentText);
        return undoStack.pop();
    }

    public String redo(String currentText) {
        if (redoStack.isEmpty()) {
            return currentText;
        }

        undoStack.push(currentText);
        return redoStack.pop();
    }
}
