
package view;

import model.TextEditor;
import util.FileManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class EditorUI {

    private final JFrame frame = new JFrame("Text Editor - Undo/Redo");
    private final JTextArea textArea = new JTextArea();
    private final JLabel statusBar = new JLabel("Ready");

    private final TextEditor editor = new TextEditor();
    private final FileManager fileManager = new FileManager();

    private Path currentFile;
    private boolean syncing = false;

    public void showWindow() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(900, 650);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setLocationRelativeTo(null);

        textArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(12, 12, 12, 12));

        frame.setJMenuBar(createMenuBar());
        frame.add(createToolbar(), BorderLayout.NORTH);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(statusBar, BorderLayout.SOUTH);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateEditor();
            }

            public void removeUpdate(DocumentEvent e) {
                updateEditor();
            }

            public void changedUpdate(DocumentEvent e) {
                updateEditor();
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                exitEditor();
            }
        });

        updateStatus();
        frame.setVisible(true);
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));

        addButton(toolbar, "New", this::newFile);
        addButton(toolbar, "Open", this::openFile);
        addButton(toolbar, "Save", this::saveFile);
        addButton(toolbar, "Save As", this::saveFileAs);
        addButton(toolbar, "Undo", this::undo);
        addButton(toolbar, "Redo", this::redo);

        return toolbar;
    }

    private void addButton(JPanel panel, String label, Runnable action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> action.run());
        panel.add(button);
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, "New", this::newFile, "control N");
        addMenuItem(fileMenu, "Open", this::openFile, "control O");
        addMenuItem(fileMenu, "Save", this::saveFile, "control S");
        addMenuItem(fileMenu, "Save As", this::saveFileAs, null);
        addMenuItem(fileMenu, "Exit", this::exitEditor, null);

        JMenu editMenu = new JMenu("Edit");
        addMenuItem(editMenu, "Undo", this::undo, "control Z");
        addMenuItem(editMenu, "Redo", this::redo, "control Y");

        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(
                frame,
                "Text Editor with Undo/Redo\nJava Swing application using two stacks.",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        ));
        helpMenu.add(about);

        bar.add(fileMenu);
        bar.add(editMenu);
        bar.add(helpMenu);

        return bar;
    }

    private void addMenuItem(
            JMenu menu, String label, Runnable action, String shortcut) {

        JMenuItem item = new JMenuItem(label);

        if (shortcut != null) {
            item.setAccelerator(KeyStroke.getKeyStroke(shortcut));
        }

        item.addActionListener(e -> action.run());
        menu.add(item);
    }

    private void updateEditor() {
        if (syncing) {
            return;
        }

        editor.replaceText(textArea.getText());
        updateStatus();
    }

    private void syncTextArea() {
        syncing = true;
        textArea.setText(editor.getCurrentText());
        syncing = false;
        updateStatus();
    }

    private void undo() {
        editor.undo();
        syncTextArea();
    }

    private void redo() {
        editor.redo();
        syncTextArea();
    }

    private void newFile() {
        if (!confirmDiscard()) {
            return;
        }

        currentFile = null;
        syncing = true;
        textArea.setText("");
        syncing = false;

        editor.replaceText("");
        frame.setTitle("Text Editor - New File");
        updateStatus();
    }

    private void openFile() {
        if (!confirmDiscard()) {
            return;
        }

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path selectedFile = chooser.getSelectedFile().toPath();

        try {
            String content = fileManager.openFile(selectedFile);

            currentFile = selectedFile;
            editor.replaceText(content);

            syncing = true;
            textArea.setText(content);
            syncing = false;

            frame.setTitle("Text Editor - " + currentFile.getFileName());
            updateStatus();

        } catch (IOException ex) {
            showError("Could not open file", ex);
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
            return;
        }

        try {
            fileManager.saveFile(currentFile, textArea.getText());
            frame.setTitle("Text Editor - " + currentFile.getFileName());
            statusBar.setText("File saved successfully.");
        } catch (IOException ex) {
            showError("Could not save file", ex);
        }
    }

    private void saveFileAs() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        currentFile = chooser.getSelectedFile().toPath();
        saveFile();
    }

    private boolean confirmDiscard() {
        if (textArea.getText().isEmpty()) {
            return true;
        }

        int result = JOptionPane.showConfirmDialog(
                frame,
                "Continue without saving your current text?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        return result == JOptionPane.YES_OPTION;
    }

    private void exitEditor() {
        if (confirmDiscard()) {
            frame.dispose();
        }
    }

    private void updateStatus() {
        String text = textArea.getText();

        int characters = text.length();
        int words = text.trim().isEmpty()
                ? 0 : text.trim().split("\\s+").length;
        int lines = text.isEmpty()
                ? 1 : text.split("\\n", -1).length;

        statusBar.setText(
                "Words: " + words
                + "    Characters: " + characters
                + "    Lines: " + lines
        );
    }

    private void showError(String message, Exception ex) {
        JOptionPane.showMessageDialog(
                frame,
                message + ":\n" + ex.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
