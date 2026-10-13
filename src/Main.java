import controller.EditorController;
import view.EditorUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditorController controller = new EditorController();
            EditorUI view = new EditorUI(controller);
            controller.attachView(view);
            view.showWindow();
        });
    }
}
