public class UndoRedoManager {

    private Stack undoStack;
    private Stack redoStack;

    public UndoRedoManager() {
        undoStack = new Stack();
        redoStack = new Stack();
    }

    // Save current text before making changes
    public void saveState(String currentText) {
        undoStack.push(currentText);
        redoStack.clear();
    }

    // Undo operation
    public String undo(String currentText) {

        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return currentText;
        }

        redoStack.push(currentText);

        return undoStack.pop();
    }

    // Redo operation
    public String redo(String currentText) {

        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return currentText;
        }

        undoStack.push(currentText);

        return redoStack.pop();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}