
package model;

public class TextEditor {
    private String currentText = "";
    private final UndoRedoManager manager = new UndoRedoManager();

    public void replaceText(String text) {
        if (text.equals(currentText)) {
            return;
        }

        manager.saveState(currentText);
        currentText = text;
    }

    public String getCurrentText() {
        return currentText;
    }

    public void undo() {
        currentText = manager.undo(currentText);
    }

    public void redo() {
        currentText = manager.redo(currentText);
    }
}
