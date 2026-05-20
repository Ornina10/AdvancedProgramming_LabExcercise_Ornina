# JavaFX Notepad Application

## About This Project

This Notepad application was developed as an assignment following our teacher's instructions. The goal was to build a simple text editor using JavaFX that includes basic file operations (New, Open, Save, Save As), editing shortcuts, a word counter, and a zoom feature to change font size.

This project reviews several units covered in our course – including JavaFX UI design, file I/O, event handling, and menu/toolbar creation.

## Features

- **New, Open, Save, Save As** – Create new files, open existing `.txt` files, and save your work.
- **Unsaved changes warning** – The app asks you to save before closing or creating a new file if there are unsaved changes.
- **Word & character count** – Real‑time counter in the status bar, plus a detailed dialog under *Tools → Word Count*.
- **Zoom In / Zoom Out** – Adjust the font size from 10 to 30 points. The zoom percentage is shown on the toolbar.
- **Cut, Copy, Paste, Select All** – Available from the *Edit* menu.
- **Status bar** – Displays the current file name and a `(modified)` marker when changes are unsaved.

## Requirements

- Java 17 or newer
- JavaFX (if your JDK does not include it, download the SDK from Gluon)

## How to Run

1. Open `NotepadApp.java` in your IDE.
2. Make sure JavaFX is added to your module path.
3. Run the `main` method.

## How to Use

- **File menu** – Create, open, save, or exit.
- **Edit menu** – Cut, copy, paste, select all.
- **Tools menu** – Show detailed word count.
- **Toolbar buttons** – Quick access to New, Open, Save, Save As, Word Count, Zoom Out, Zoom In.
- **Zoom** – Click `A-` to shrink text, `A+` to enlarge. The current zoom percentage updates next to the buttons.

## Notes

- Files are saved and opened as UTF‑8 text files.
- The default font size is 14 points (100%). Zoom changes are not saved between sessions.
