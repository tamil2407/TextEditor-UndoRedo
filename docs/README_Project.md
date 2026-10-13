# Project Documentation: Text Editor with Undo/Redo

## Overview

This project is a Java Swing desktop text editor. It demonstrates object-oriented design and a custom two-stack history mechanism for undo and redo. The editor keeps the active document in memory, lets the user work with files through standard dialogs, and displays live document statistics.

## Application workflow

```text
Main
  └─ starts the Swing UI thread
       └─ EditorUI
            ├─ accepts document edits
            ├─ delegates text state to TextEditor
            │    └─ delegates history to UndoRedoManager
            │         ├─ undo Stack
            │         └─ redo Stack
            └─ uses FileManager for UTF-8 file I/O
```

## Source layout

| Path | Responsibility |
| --- | --- |
| `src/Main.java` | Launches the application window on Swing’s event-dispatch thread. |
| `src/view/EditorUI.java` | Implements the window, menus, toolbar, text area, status bar, dialogs, and UI actions. |
| `src/model/TextEditor.java` | Maintains the current text and coordinates history operations. |
| `src/model/UndoRedoManager.java` | Stores and restores prior states using undo and redo stacks. |
| `src/model/Stack.java` | Provides `push`, `pop`, `peek`, `isEmpty`, and `clear` for `String` states. |
| `src/util/FileManager.java` | Opens and saves UTF-8 text using `java.nio.file.Files`. |

The `src/controller`, `test`, and `assets` directories are currently reserved for future controller classes, automated tests, and visual assets respectively.

## User interface

The application window includes:

- A File menu: New, Open, Save, Save As, and Exit
- An Edit menu: Undo and Redo
- A Help menu: About
- A toolbar for New, Open, Save, Save As, Undo, and Redo
- A word-wrapping text area
- A status bar with word, character, and line counts

Keyboard shortcuts are Ctrl+N, Ctrl+O, Ctrl+S, Ctrl+Z, and Ctrl+Y. File operations report I/O failures in an error dialog. Starting a new document, opening another file, or closing the window requests confirmation before non-empty text is discarded.

## Undo/redo algorithm

1. A user edit triggers the text-area document listener.
2. `TextEditor.replaceText` saves the old text through `UndoRedoManager` and records the new text.
3. Saving a new state pushes the old value to the undo stack and clears the redo stack.
4. Undo pushes the current text to the redo stack, then restores the most recent undo state.
5. Redo pushes the current text to the undo stack, then restores the most recent redo state.
6. If the relevant stack is empty, the document remains unchanged.

## Build and launch

Run these commands from the repository root with JDK 11 or later:

```powershell
javac -d out src/Main.java src/model/*.java src/util/*.java src/view/*.java
java -cp out Main
```

Compiled files are produced in `out/`; they are ignored by Git.

## Current limitations

- History is kept only in memory and is reset when the application closes.
- Undo/redo snapshots cover complete text-area changes rather than character-level operations.
- The editor does not track a separate “unsaved changes” state; confirmation is based on whether the text area is non-empty.
- There are no automated tests yet.

## Future enhancements

- Add document-dirty tracking and save-before-close prompts
- Add Cut, Copy, Paste, Find, and Replace
- Add configurable themes and font settings
- Add automated tests for the model and file utility
- Add icons and screenshots under `assets/`
