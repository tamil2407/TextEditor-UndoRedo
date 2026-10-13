import model.TextEditor;

public class TextEditorTest {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.setText("Hello");
        editor.setText("Hello World");
        check("Hello World".equals(editor.getText()), "set text");
        editor.undo();
        check("Hello".equals(editor.getText()), "undo");
        editor.redo();
        check("Hello World".equals(editor.getText()), "redo");
        editor.undo();
        editor.setText("Hello Java");
        editor.redo();
        check("Hello Java".equals(editor.getText()), "new edit clears redo history");
        editor.loadText("Loaded");
        check("Loaded".equals(editor.getText()), "load text");
        check(!editor.canUndo(), "load clears history");
        System.out.println("All TextEditor tests passed.");
    }
    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError("Failed: " + label);
    }
}
