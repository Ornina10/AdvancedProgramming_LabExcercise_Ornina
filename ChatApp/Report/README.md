
```markdown
# JavaFX Chat Application

## About This Project

This chat application was developed as an assignment following our teacher's instructions. The goal was to build a client‑server program using JavaFX where multiple people can connect, pick a username, and send messages to everyone else in the room. The server handles all the coordination using threads to manage multiple clients simultaneously, while the client provides a simple graphical interface.

This project reviews many units covered in our course including multithreading, socket programming, GUI development with JavaFX, file I/O, and database connectivity. Every feature (saving chat logs to files, exporting conversations, etc.) was required by our teacher and implemented as requested.

## What the Application Does

- Real‑time messaging – When someone sends a message, everyone connected sees it immediately.
- Unique usernames – The server checks for duplicate names when a client logs in and rejects any that are already taken.
- Persistent logs – Every message (and system notification) is written to `logs/chat.log` on the server side. There is also a snapshot of currently online users saved to `logs/online-users.txt`.
- Chat export for clients – From the client window, you can click a button to save the entire chat history as a `.txt` file wherever you like.
-Optional database storage – I added MySQL support as an extra. If a database is available, the server stores users and messages there. If not, the application still runs fine – it simply falls back to file‑only logging.

## Technical Requirements

To run this application you will need:

- Java 17 or newer
- JavaFX
-MySQL

> **Note on MySQL:** I used **XAMPP** to run MySQL locally. Simply start the Apache and MySQL modules from the XAMPP control panel before launching the server.

## How to Run the Application

### Starting the server

Open and run `ChatServerApp.java`. A window will appear. Click the **Start Server** button – it will listen on port 5555 by default.

### Starting one or more clients

Open and run `ChatClientApp.java`. Enter a username of your choice, leave the host and port as they are (`127.0.0.1` and `5555`), and click **Connect**. You can repeat this step on the same machine to simulate multiple users.

### Sending messages

Type something in the text field at the bottom and press **Send** or hit the **Enter** key. The message will appear on every connected client’s screen.
```
