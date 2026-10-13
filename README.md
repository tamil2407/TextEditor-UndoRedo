# Text Editor with Undo/Redo

A desktop text editor written in Java Swing. It uses a custom two-stack undo/redo implementation and supports creating, opening, editing, and saving UTF-8 text files.

## Features

- Create a new document
- Open and save text files
- Save documents under a new name
- Undo and redo complete text changes
- Edit through a word-wrapping text area
- View live word, character, and line counts
- Use toolbar actions, menus, or keyboard shortcuts

## Requirements

- Java Development Kit (JDK) 11 or later

## Run

From the project root:

```powershell
javac -d out src/Main.java src/model/*.java src/util/*.java src/view/*.java
java -cp out Main
```

The application opens in a Swing window.

## Controls

| Action | Menu / toolbar | Shortcut |
| --- | --- | --- |
| New document | File > New | Ctrl+N |
| Open file | File > Open | Ctrl+O |
| Save file | File > Save | Ctrl+S |
| Save under a new name | File > Save As | — |
| Undo | Edit > Undo | Ctrl+Z |
| Redo | Edit > Redo | Ctrl+Y |
| Exit | File > Exit | — |

New, Open, and Exit ask for confirmation before discarding non-empty text.

## Project structure

```text
TextEditor-UndoRedo/
├── assets/                    # Reserved for icons and screenshots
├── docs/
│   ├── FlowDiagram.png
│   └── README_Project.md      # Detailed project documentation
├── output/
│   └── SampleOutput.txt       # Example application interaction
├── src/
│   ├── Main.java              # Application entry point
│   ├── controller/            # Reserved for controller classes
│   ├── model/
│   │   ├── Stack.java
│   │   ├── TextEditor.java
│   │   └── UndoRedoManager.java
│   ├── util/
│   │   └── FileManager.java
│   └── view/
│       └── EditorUI.java
└── test/                      # Reserved for automated tests
```

`out/` contains compiled `.class` files and is intentionally excluded from version control.

## Architecture

- `Main` starts the Swing event-dispatch thread and shows `EditorUI`.
- `EditorUI` owns the window, menus, toolbar, document listener, file chooser, and status bar.
- `TextEditor` holds the current document text and delegates history operations.
- `UndoRedoManager` stores document states in separate undo and redo stacks.
- `Stack` is the project’s `ArrayList<String>`-backed stack implementation.
- `FileManager` reads and writes UTF-8 files.

## Undo and redo behavior

Each edit saves the previous document state on the undo stack and clears redo history. Undo moves the current state to the redo stack and restores the latest undo state. Redo performs the reverse operation.

## Documentation and sample

- [Detailed project documentation](docs/README_Project.md)
- [Example interaction](output/SampleOutput.txt)

## License

Educational project.