package model;

/** Document model and undo/redo API. */
public class TextEditor {
    private String text = "";
    private final UndoRedoManager history = new UndoRedoManager();

    public String getText() { return text; }

    public void setText(String value) {
        if (value == null) value = "";
        if (value.equals(text)) return;
        history.recordChange(text);
        text = value;
    }

    /** Replaces the document without adding an undo step. */
    public void loadText(String value) {
        text = value == null ? "" : value;
        history.clearHistory();
    }

    public void undo() { text = history.undo(text); }
    public void redo() { text = history.redo(text); }
    public boolean canUndo() { return history.canUndo(); }
    public boolean canRedo() { return history.canRedo(); }
    public void clear() { loadText(""); }
}
