public class TextEditor {

    private String currentText;
    private UndoRedoManager manager;

    public TextEditor() {
        currentText = "";
        manager = new UndoRedoManager();
    }

    // Add new text
    public void typeText(String text) {
        manager.saveState(currentText);
        currentText += text;
    }

    // Undo last operation
    public void undo() {
        currentText = manager.undo(currentText);
    }

    // Redo last operation
    public void redo() {
        currentText = manager.redo(currentText);
    }

    // Display current text
    public void showText() {
        System.out.println("\nCurrent Text: " + currentText);
    }

    // Get current text
    public String getCurrentText() {
        return currentText;
    }
}