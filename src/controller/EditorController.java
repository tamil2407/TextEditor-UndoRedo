package controller;

import model.TextEditor;
import util.FileManager;
import view.EditorUI;
import java.io.IOException;
import java.nio.file.Path;

/** Coordinates user actions between the UI, model, and file utility. */
public class EditorController {
    private final TextEditor model = new TextEditor();
    private final FileManager files = new FileManager();
    private EditorUI view;
    private Path currentFile;
    private boolean dirty;

    public void attachView(EditorUI view) { this.view = view; }
    public TextEditor getModel() { return model; }
    public Path getCurrentFile() { return currentFile; }
    public boolean isDirty() { return dirty; }

    public void textChanged(String value) {
        if (value.equals(model.getText())) return;
        model.setText(value);
        dirty = true;
        notifyView();
    }
    public void undo() { model.undo(); dirty = true; notifyView(); }
    public void redo() { model.redo(); dirty = true; notifyView(); }

    public void newDocument() {
        model.clear();
        currentFile = null;
        dirty = false;
        notifyView();
    }
    public void open(Path path) throws IOException {
        String value = files.openFile(path);
        model.loadText(value);
        currentFile = path;
        dirty = false;
        notifyView();
    }
    public void save() throws IOException {
        if (currentFile == null) throw new IllegalStateException("Use Save As to choose a file.");
        files.saveFile(currentFile, model.getText());
        dirty = false;
        notifyView();
    }
    public void saveAs(Path path) throws IOException {
        files.saveFile(path, model.getText());
        currentFile = path;
        dirty = false;
        notifyView();
    }
    private void notifyView() {
        if (view != null) {
            view.refreshFromController();
            view.updateWindowTitle(currentFile, dirty);
            view.updateStatus(model.getText());
        }
    }
}
