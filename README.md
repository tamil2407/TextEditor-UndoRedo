# Text Editor with Undo/Redo

A Java Swing desktop text editor demonstrating low-level design and undo/redo using two stacks.

## Features
- Swing desktop interface, toolbar, and File/Edit/View/Help menus
- Undo/Redo using two stacks of text snapshots
- New, Open, Save, and Save As for UTF-8 plain-text files
- Ctrl+N, Ctrl+O, Ctrl+S, Ctrl+Shift+S, Ctrl+Z, Ctrl+Y
- Live word, character, and line counts
- Light/dark theme toggle
- Unsaved-change prompt

## Requirements
JDK 11 or newer. No third-party libraries required.

## Compile and run (PowerShell, repository root)
```powershell
Remove-Item out -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force out
javac -d out src\Main.java src\model\Stack.java src\model\UndoRedoManager.java src\model\TextEditor.java src\util\FileManager.java src\util\ThemeManager.java src\controller\EditorController.java src\view\EditorUI.java
java -cp out Main
```

## Test the model
```powershell
javac -cp out -d out test\TextEditorTest.java
java -cp out TextEditorTest
```
Expected: `All TextEditor tests passed.`

## Structure
```text
TextEditor-UndoRedo/
├── README.md
├── .gitignore
├── docs/FlowDiagram.png
├── docs/README_Project.md
├── output/SampleOutput.txt
├── assets/screenshots/
├── assets/icons/
├── src/Main.java
├── src/model/{Stack,UndoRedoManager,TextEditor}.java
├── src/view/EditorUI.java
├── src/controller/EditorController.java
├── src/util/{FileManager,ThemeManager}.java
└── test/TextEditorTest.java
```

## Architecture
- **Model:** document state and history.
- **View:** Swing window and controls.
- **Controller:** coordinates view, model, and file actions.
- **Utilities:** UTF-8 file operations and theme palettes.

See `docs/README_Project.md` and `docs/FlowDiagram.png`.

## Author
Tamilvel Mugunthan S — Electrical and Electronics Engineering (EEE)  
Roll Number: _Add your roll number_

Educational project. Add a license file if you plan to publish under a specific open-source license.
