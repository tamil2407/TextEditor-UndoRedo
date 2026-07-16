# Text Editor with Undo/Redo using Two Stacks

## Overview

This project is a simple console-based **Text Editor** developed in **Java** that demonstrates the implementation of the **Undo** and **Redo** operations using the **Two Stacks** data structure. It is designed as a Low-Level Design (LLD) project to showcase object-oriented programming principles and efficient state management.

---

## Problem Statement

Develop a text editor that allows users to type text and perform **Undo** and **Redo** operations efficiently using two stacks.

---

## Objectives

* Implement a text editor using Java.
* Demonstrate the use of the Stack data structure.
* Implement Undo and Redo functionality.
* Apply Object-Oriented Programming (OOP) concepts.
* Create a simple and user-friendly console application.

---

## Features

* Add text to the editor.
* Undo the last operation.
* Redo the previously undone operation.
* Display the current text.
* Menu-driven console interface.
* Custom Stack implementation.

---

## Technologies Used

* **Language:** Java
* **IDE:** Visual Studio Code
* **Version Control:** Git
* **Repository Hosting:** GitHub

---

## Data Structure Used

### Two Stacks

The application uses two stacks:

### Undo Stack

Stores the previous states of the text before every modification.

### Redo Stack

Stores the states removed during an Undo operation, allowing them to be restored using Redo.

---

## Project Structure

```text
TextEditor-UndoRedo
│
├── README.md
├── .gitignore
│
├── docs
│   ├── README_Project.md
│   ├── UseCaseDiagram.png
│   ├── ClassDiagram.png
│   ├── SequenceDiagram.png
│   ├── ActivityDiagram.png
│   └── LLD_Report.pdf
│
├── output
│   └── SampleOutput.txt
│
└── src
    ├── Main.java
    ├── Stack.java
    ├── UndoRedoManager.java
    └── TextEditor.java
```

---

## Class Description

### Stack.java

Implements a custom stack using `ArrayList<String>`.

**Methods**

* push()
* pop()
* peek()
* isEmpty()
* size()
* clear()

---

### UndoRedoManager.java

Manages the Undo and Redo stacks.

**Responsibilities**

* Save text states
* Undo operations
* Redo operations

---

### TextEditor.java

Handles the editor's business logic.

**Functions**

* Type text
* Undo
* Redo
* Display current text

---

### Main.java

Provides the console-based user interface and menu-driven interaction.

---

## Algorithm

1. Start the application.
2. Initialize the Undo Stack and Redo Stack.
3. Display the menu.
4. Read the user's choice.
5. If the user types text:

   * Save the current state to the Undo Stack.
   * Append the new text.
   * Clear the Redo Stack.
6. If Undo is selected:

   * Move the current state to the Redo Stack.
   * Restore the previous state from the Undo Stack.
7. If Redo is selected:

   * Move the current state to the Undo Stack.
   * Restore the latest state from the Redo Stack.
8. Display the current text whenever requested.
9. Exit when the user chooses Exit.

---

## Sample Output

```text
==============================
 TEXT EDITOR WITH UNDO/REDO
==============================

1. Type Text
2. Undo
3. Redo
4. Show Current Text
5. Exit

Enter your choice: 1

Enter text: hello

Enter your choice: 1

Enter text: world

Enter your choice: 4

Current Text: helloworld

Enter your choice: 2

Enter your choice: 4

Current Text: hello

Enter your choice: 3

Enter your choice: 4

Current Text: helloworld
```

---

## OOP Concepts Used

* Encapsulation
* Abstraction
* Modularity
* Object Composition

---

## Future Enhancements

* GUI using Java Swing or JavaFX
* File Save/Open functionality
* Copy, Cut, and Paste operations
* Search and Replace
* Keyboard shortcuts
* Rich text editing

---

## Learning Outcomes

* Understanding of Stack data structures.
* Practical implementation of Undo/Redo.
* Experience with Java object-oriented programming.
* Hands-on use of Git and GitHub for version control.
* Exposure to Low-Level Design principles.

---

## Author

**Tamilvel Mugunthan**

Electrical and Electronics Engineering (EEE)

---

## License

This project is developed for educational and academic purposes.
