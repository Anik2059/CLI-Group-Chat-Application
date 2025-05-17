# CLI Group Chat Application

This is a command-line interface (CLI) group chat application built in Java using socket programming. It enables multiple clients to connect to a server, send messages, and receive real-time updates in a user-friendly interface. The project is designed for educational purposes, with simple, well-commented code suitable for beginners learning Java networking and threading.
Features

Server:
Connects multiple clients on localhost:12345.
Broadcasts messages and tracks usernames.
Sends join/leave notifications and user lists.


Client Interface:
Connected Users: Displays online users (e.g., - Anik, - Hasib).
System Messages: Shows join/leave notifications (e.g., Hasib has joined the chat!).
Client Messages: Shows user messages (e.g., Anik: Hi!).
Send Message: Provides a Type message: prompt for input.


Real-time updates: Messages from other clients appear instantly.
Thread-safe design with synchronized console output to prevent garbled displays.
Minimal dependencies (requires only Java).

Prerequisites

Java Development Kit (JDK): Version 8 or higher.
Terminal: Linux, macOS Terminal, Windows PowerShell, or CMD (for terminal-based execution).
Apache NetBeans IDE: Version 21 or similar (optional, for IDE-based execution).
Git: For cloning the repository.
Basic understanding of Java and running programs in a terminal or IDE.

Setup and Installation

Clone the Repository:git clone https://github.com/your-username/cli-group-chat.git
cd cli-group-chat


Project Structure:
ChatServer.java: Server program handling client connections and messages.
ChatClient.java: Client program with the chat interface.
LabReport.md: Lab report documenting the experiment.
README.md: This file.
LICENSE: MIT License file.



# How to Run

Option 1: Using a Terminal

Compile the Code:javac ChatServer.java ChatClient.java


Start the Server:java ChatServer


Output: Starting chat server on port 12345...
The server runs on localhost:12345 and waits for clients.


Start Clients (in separate terminals):java ChatClient


Enter a username (e.g., Anik) when prompted.
The interface shows Connected Users, System Messages, Client Messages, and Type message:.


Chat:
Type messages at Type message: and press Enter to send.
Messages from other clients appear instantly in the Client Messages section.
Type /quit to disconnect.


Stop the Server:
Press Ctrl+C in the server terminal.



Option 2: Using NetBeans

Open NetBeans:
Launch Apache NetBeans IDE (e.g., version 21).


Create a New Project:
Go to File > New Project.
Select Java > Java Application and click Next.
Set the project name (e.g., CLIGroupChat) and choose a location.
Uncheck Create Main Class and click Finish.


Add Source Files:
Copy ChatServer.java and ChatClient.java into the project’s source folder:
Right-click Source Packages (usually <default package>) in the Projects pane.
Select New > Java Class, but instead, drag or paste the two .java files into the folder via your file explorer.
Ensure both files appear under <default package> or create a package (e.g., chat) and add package chat; to both files.




Run the Server:
Right-click ChatServer.java in the Projects pane and select Run File.
The NetBeans Output window shows:Starting chat server on port 12345...


The server runs on localhost:12345.


Run Clients:
Right-click ChatClient.java and select Run File.
In the Output window, enter a username (e.g., Anik) at Username: and press Enter.
The interface appears with four sections and Type message:.
To run multiple clients:
Option A: Open another NetBeans instance (File > Open Project, select the same project), and run ChatClient.java again.
Option B: Create multiple Run configurations:
Go to Run > Set Project Configuration > Customize.
Add configurations (e.g., Client1, Client2) with Main Class set to ChatClient.
Run each configuration via Run > Run Main Project (selecting different configurations).




Each client runs in a separate Output tab or window.


Chat:
Type messages at Type message: in each client’s console and press Enter.
Messages from other clients appear instantly.
Type /quit to disconnect a client.


Stop the Server:
Click the red Stop button in the NetBeans Output window for ChatServer.java or close the window.



Example Usage

Start the server (in NetBeans or terminal):Starting chat server on port 12345...


Start Client 1 (Anik) in NetBeans:Connected to chat server!
Enter your username:
Username: Anik
=== Connected Users ===
- Anik

=== System Messages ===
Anik has joined the chat!

=== Client Messages ===
(No client messages)

=== Send Message ===
Type message: 


Start Client 2 (Hasib):
Anik’s interface updates instantly:

=== Connected Users ===
- Anik
- Hasib

=== System Messages ===
Anik has joined the chat!
Hasib has joined the chat!

=== Client Messages ===
(No client messages)

=== Send Message ===
Type message: Hi!


Hasib sends Hello, Anik sees it immediately:=== Connected Users ===
- Anik
- Hasib

=== System Messages ===
Anik has joined the chat!
Hasib has joined the chat!

=== Client Messages ===
Anik: Hi!
Hasib: Hello

=== Send Message ===
Type message: 



# Notes

Console Compatibility:
The interface uses \033[H\033[2J to clear the console, which works in Linux, macOS, Windows PowerShell, and NetBeans’ Output window. In Windows CMD or some IDEs, it may not clear properly, causing output to stack.
In NetBeans, ensure the Output window is focused to enter usernames or messages.


NetBeans Tips:
Verify JDK is set up in Tools > Java Platforms.
Use separate NetBeans instances or Run configurations for multiple clients to avoid console conflicts.
If the console doesn’t accept input, ensure ChatClient.java is running and the Output tab is active.


Limitations:
Slight flickering may occur due to console clearing.
No duplicate username handling (two users can be Hasib).
Input may be interrupted if a message arrives while typing, as the interface redraws.


Future Improvements:
Add checks for duplicate usernames (e.g., Hasib2).
Include timestamps on messages (e.g., Hasib [05:04 PM]: Hello).
Use a console library (e.g., JLine) for smoother rendering and input preservation.



# Contributing

Contributions are welcome! To contribute:

Fork the repository.
Create a new branch (git checkout -b feature/your-feature).
Make changes and commit (git commit -m "Add your feature").
Push to your branch (git push origin feature/your-feature).
Open a pull request with a clear description of your changes.

Ideas for contributions include private messaging, timestamps, or improved console handling.
License
This project is licensed under the MIT License. See the LICENSE file for details.

# Contributors

Md. Hasibul Islam Anik - Developer and project creator.

Acknowledgments

Developed as a lab experiment for learning Java socket programming.
Inspired by beginner-friendly networking tutorials and simple chat applications.
Thanks to instructors and peers for feedback during development.



