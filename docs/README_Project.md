# Project Documentation

## Overview
A Java Swing text editor that demonstrates a two-stack undo/redo design with a simple desktop interface.

## Problem Statement
Text editors need to reverse recent edits and restore edits that were undone. Two stacks can maintain prior and redoable document snapshots.

## Objectives
- Create a desktop editor using Java Swing.
- Implement undo and redo using LIFO stacks.
- Separate model, view, controller, and utilities.
- Support UTF-8 plain-text file operations.
- Display live word, character, and line counts.

## Methodology
1. The user edits text in the Swing text area.
2. The controller forwards changes to the model.
3. Before a change, the previous document snapshot is pushed onto the undo stack.
4. Undo pushes the current state onto the redo stack and restores the previous state.
5. Redo pushes the current state onto the undo stack and restores the next state.
6. FileManager reads and writes UTF-8 text files.

## Components
- `Main`: application entry point.
- `model.Stack`: LIFO collection for snapshots.
- `model.UndoRedoManager`: undo/redo history logic.
- `model.TextEditor`: current document state.
- `view.EditorUI`: Swing UI, menus, toolbar, status bar.
- `controller.EditorController`: coordinates UI, model, and files.
- `util.FileManager`: text-file reading and writing.
- `util.ThemeManager`: light and dark color palettes.

## Algorithms
**Undo:** if undo history exists, push current text to redo, then restore the top undo snapshot.  
**Redo:** if redo history exists, push current text to undo, then restore the top redo snapshot.  
A new edit clears redo history.

## Technologies
Java JDK 11+, Java Swing, VS Code, Git, GitHub, PowerShell.

## Limitations and future work
Undo history is in-memory and plain-text formats are currently supported. Future work may include Find/Replace, autosave, PDF/DOCX export, and GUI automation tests.
