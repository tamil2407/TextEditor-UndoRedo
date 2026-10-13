package view;

import controller.EditorController;
import util.ThemeManager;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class EditorUI {
    private final EditorController controller;
    private final JFrame frame = new JFrame("Text Editor - Untitled");
    private final JTextArea area = new JTextArea();
    private final JLabel status = new JLabel("Ready");
    private boolean syncing;
    private boolean dark;

    public EditorUI(EditorController controller) { this.controller = controller; }

    public void showWindow() {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setMinimumSize(new Dimension(650, 450));
        frame.setLocationRelativeTo(null);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(14,14,14,14));
        frame.setJMenuBar(menuBar());
        frame.add(toolbar(), BorderLayout.NORTH);
        frame.add(new JScrollPane(area), BorderLayout.CENTER);
        status.setBorder(BorderFactory.createEmptyBorder(6,10,6,10));
        frame.add(status, BorderLayout.SOUTH);

        area.getDocument().addDocumentListener(new DocumentListener() {
            private void changed() {
                if (!syncing) controller.textChanged(area.getText());
                updateStatus(area.getText());
            }
            public void insertUpdate(DocumentEvent e) { changed(); }
            public void removeUpdate(DocumentEvent e) { changed(); }
            public void changedUpdate(DocumentEvent e) { changed(); }
        });
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { exit(); }
        });
        applyTheme();
        updateStatus("");
        frame.setVisible(true);
    }

    private JPanel toolbar() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        addButton(p, "New", this::newFile); addButton(p, "Open", this::openFile);
        addButton(p, "Save", this::saveFile); addButton(p, "Save As", this::saveAs);
        addButton(p, "Undo", controller::undo); addButton(p, "Redo", controller::redo);
        addButton(p, "Toggle Theme", this::toggleTheme);
        return p;
    }
    private void addButton(JPanel p, String title, Runnable action) {
        JButton b = new JButton(title); b.addActionListener(e -> action.run()); p.add(b);
    }
    private JMenuBar menuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        addItem(file,"New",this::newFile,"control N");
        addItem(file,"Open...",this::openFile,"control O");
        addItem(file,"Save",this::saveFile,"control S");
        addItem(file,"Save As...",this::saveAs,"control shift S");
        file.addSeparator(); addItem(file,"Exit",this::exit,null);
        JMenu edit = new JMenu("Edit");
        addItem(edit,"Undo",controller::undo,"control Z");
        addItem(edit,"Redo",controller::redo,"control Y");
        JMenu view = new JMenu("View");
        addItem(view,"Toggle Light/Dark Theme",this::toggleTheme,null);
        JMenu help = new JMenu("Help");
        addItem(help,"About",() -> JOptionPane.showMessageDialog(frame,
            "Text Editor with Undo/Redo\nJava Swing • Two-stack history",
            "About",JOptionPane.INFORMATION_MESSAGE),null);
        bar.add(file); bar.add(edit); bar.add(view); bar.add(help);
        return bar;
    }
    private void addItem(JMenu menu, String title, Runnable action, String shortcut) {
        JMenuItem item = new JMenuItem(title);
        if (shortcut != null) item.setAccelerator(KeyStroke.getKeyStroke(shortcut));
        item.addActionListener(e -> action.run()); menu.add(item);
    }

    private void newFile() {
        if (!confirmDiscard()) return;
        controller.newDocument();
    }
    private void openFile() {
        if (!confirmDiscard()) return;
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) return;
        try { controller.open(chooser.getSelectedFile().toPath()); }
        catch (IOException ex) { error("Unable to open file", ex); }
    }
    private void saveFile() {
        if (controller.getCurrentFile() == null) { saveAs(); return; }
        try { controller.save(); status.setText("Saved successfully."); }
        catch (IOException | IllegalStateException ex) { error("Unable to save file", ex); }
    }
    private void saveAs() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) return;
        Path path = chooser.getSelectedFile().toPath();
        try { controller.saveAs(path); status.setText("Saved successfully."); }
        catch (IOException ex) { error("Unable to save file", ex); }
    }
    private boolean confirmDiscard() {
        if (!controller.isDirty()) return true;
        int choice = JOptionPane.showConfirmDialog(frame,
            "Save changes before continuing?", "Unsaved Changes",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) return false;
        if (choice == JOptionPane.YES_OPTION) {
            saveFile();
            return !controller.isDirty();
        }
        return choice == JOptionPane.NO_OPTION;
    }
    private void exit() { if (confirmDiscard()) frame.dispose(); }

    public void refreshFromController() {
        syncing = true;
        area.setText(controller.getModel().getText());
        syncing = false;
        updateStatus(area.getText());
    }
    public void updateWindowTitle(Path path, boolean dirty) {
        String name = path == null ? "Untitled" : path.getFileName().toString();
        frame.setTitle("Text Editor - " + name + (dirty ? " *" : ""));
    }
    public void updateStatus(String text) {
        int words = text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
        int lines = text.isEmpty() ? 1 : text.split("\\R", -1).length;
        status.setText("Words: " + words + "    Characters: " + text.length() + "    Lines: " + lines);
    }
    private void toggleTheme() { dark = !dark; applyTheme(); }
    private void applyTheme() {
        ThemeManager.Palette p = dark ? ThemeManager.dark() : ThemeManager.light();
        frame.getContentPane().setBackground(p.background);
        area.setBackground(p.editorBackground); area.setForeground(p.editorForeground);
        area.setCaretColor(p.caret);
        status.setOpaque(true); status.setBackground(p.statusBackground); status.setForeground(p.foreground);
        SwingUtilities.updateComponentTreeUI(frame);
        area.setBackground(p.editorBackground); area.setForeground(p.editorForeground);
        area.setCaretColor(p.caret);
        status.setBackground(p.statusBackground); status.setForeground(p.foreground);
    }
    private void error(String message, Exception ex) {
        JOptionPane.showMessageDialog(frame, message + ":\n" + ex.getMessage(),
            "File Error", JOptionPane.ERROR_MESSAGE);
    }
}
