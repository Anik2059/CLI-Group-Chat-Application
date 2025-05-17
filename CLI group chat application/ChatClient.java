/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatserver;

import java.io.*;
import java.net.*;
import java.util.*;

// Simple chat client with a clean interface that updates in real-time
public class ChatClient {
    // Server connection details
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    // Lists to store data
    private static List<String> systemMessages = new ArrayList<>(); // Join/leave messages
    private static List<String> clientMessages = new ArrayList<>(); // User messages
    private static List<String> connectedUsers = new ArrayList<>(); // Usernames
    // Lock for console output to prevent messy displays
    private static final Object consoleLock = new Object();

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to chat server!");

            // Set up streams for talking to the server
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
            Scanner consoleIn = new Scanner(System.in);

            // Get username from user
            System.out.println(serverIn.readLine()); // Server says "Enter your username:"
            System.out.print("Username: ");
            String username = consoleIn.nextLine();
            serverOut.println(username);

            // Show initial interface
            showInterface();

            // Start a thread to listen for server messages
            Thread serverListener = new Thread(() -> {
                try {
                    String message;
                    // Keep reading messages from the server
                    while ((message = serverIn.readLine()) != null) {
                        // Lock lists to avoid problems with multiple threads
                        synchronized (systemMessages) {
                            if (message.startsWith("MESSAGE:")) {
                                // Add user message (e.g., "Hasib: Hello")
                                clientMessages.add(message.substring(8));
                            } else if (message.startsWith("JOIN:")) {
                                // Add join/leave message (e.g., "Hasib has joined the chat!")
                                systemMessages.add(message.substring(5));
                            } else if (message.startsWith("USERLIST:")) {
                                // Update list of connected users
                                String[] users = message.substring(9).split(",");
                                connectedUsers.clear();
                                for (String user : users) {
                                    if (!user.isEmpty()) {
                                        connectedUsers.add(user);
                                    }
                                }
                            }
                        }
                        // Update interface to show new message
                        showInterface();
                    }
                } catch (IOException e) {
                    System.out.println("Lost connection to server: " + e.getMessage());
                }
            });
            serverListener.start();

            // Main loop: get user input, send messages
            while (true) {
                // Get user message
                System.out.print("Type message: ");
                String message = consoleIn.nextLine();

                // Check if user wants to quit
                if (message.equalsIgnoreCase("/quit")) {
                    socket.close();
                    break;
                }

                // Send message to server
                serverOut.println(message);

                // Update interface to show sent message
                showInterface();
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
        System.out.println("Disconnected from server.");
    }

    // Show the chat interface with all sections
    private static void showInterface() {
        // Lock console to prevent messy output from multiple threads
        synchronized (consoleLock) {
            // Clear the console (works in most terminals)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Section 1: Show connected users
            System.out.println("=== Connected Users ===");
            synchronized (connectedUsers) {
                if (connectedUsers.isEmpty()) {
                    System.out.println("(No users connected)");
                } else {
                    for (String user : connectedUsers) {
                        System.out.println("- " + user);
                    }
                }
            }

            // Section 2: Show system messages (join/leave)
            System.out.println("\n=== System Messages ===");
            synchronized (systemMessages) {
                if (systemMessages.isEmpty()) {
                    System.out.println("(No system messages)");
                } else {
                    // Show last 5 messages to keep it short
                    int start = Math.max(0, systemMessages.size() - 5);
                    for (int i = start; i < systemMessages.size(); i++) {
                        System.out.println(systemMessages.get(i));
                    }
                }
            }

            // Section 3: Show client messages
            System.out.println("\n=== Client Messages ===");
            synchronized (clientMessages) {
                if (clientMessages.isEmpty()) {
                    System.out.println("(No client messages)");
                } else {
                    // Show last 5 messages to keep it short
                    int start = Math.max(0, clientMessages.size() - 5);
                    for (int i = start; i < clientMessages.size(); i++) {
                        System.out.println(clientMessages.get(i));
                    }
                }
            }

            // Section 4: Input section
            System.out.println("\n=== Send Message ===");
        }
    }
}