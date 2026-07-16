import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TextEditor editor = new TextEditor();

        int choice;

        do {

            System.out.println("\n==============================");
            System.out.println(" TEXT EDITOR WITH UNDO/REDO");
            System.out.println("==============================");
            System.out.println("1. Type Text");
            System.out.println("2. Undo");
            System.out.println("3. Redo");
            System.out.println("4. Show Current Text");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter text: ");
                    String text = scanner.nextLine();
                    editor.typeText(text);
                    break;

                case 2:
                    editor.undo();
                    break;

                case 3:
                    editor.redo();
                    break;

                case 4:
                    editor.showText();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        scanner.close();
    }
}