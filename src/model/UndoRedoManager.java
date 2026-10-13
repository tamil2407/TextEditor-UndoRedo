package model;

/** Stores document snapshots for undo and redo. */
public class UndoRedoManager {
    private final Stack<String> undoStack = new Stack<>();
    private final Stack<String> redoStack = new Stack<>();

    public void recordChange(String previousText) {
        undoStack.push(previousText);
        redoStack.clear();
    }
    public String undo(String currentText) {
        if (undoStack.isEmpty()) return currentText;
        redoStack.push(currentText);
        return undoStack.pop();
    }
    public String redo(String currentText) {
        if (redoStack.isEmpty()) return currentText;
        undoStack.push(currentText);
        return redoStack.pop();
    }
    public void clearHistory() {
        undoStack.clear();
        redoStack.clear();
    }
    public boolean canUndo() { return !undoStack.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }
}
