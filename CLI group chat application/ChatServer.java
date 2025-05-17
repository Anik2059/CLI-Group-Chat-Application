/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatserver;

import java.io.*;
import java.net.*;
import java.util.*;

// Simple chat server to handle multiple clients and track usernames
public class ChatServer {
    private static final int PORT = 12345;
    // List to keep track of client output streams
    private static List<PrintWriter> clients = new ArrayList<>();
    // List to keep track of client usernames
    private static List<String> usernames = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Starting chat server on port " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Accept new client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // Create a new thread to handle the client
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    // Class to handle each client connection
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Set up input and output streams
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Get username from client
                out.println("Enter your username:");
                username = in.readLine();
                if (username == null || username.trim().isEmpty()) {
                    username = "Anonymous";
                }

                // Add client to the lists
                synchronized (clients) {
                    clients.add(out);
                    usernames.add(username);
                }

                // Send current username list to the new client
                out.println("USERLIST:" + String.join(",", usernames));

                // Broadcast that a new user has joined
                broadcast("JOIN:" + username + " has joined the chat!");
                // Broadcast updated username list to all clients
                broadcast("USERLIST:" + String.join(",", usernames));

                String message;
                // Read messages from client and broadcast them
                while ((message = in.readLine()) != null) {
                    if (!message.trim().isEmpty()) {
                        broadcast("MESSAGE:" + username + ": " + message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error with client " + username + ": " + e.getMessage());
            } finally {
                // Clean up when client disconnects
                if (username != null) {
                    broadcast("JOIN:" + username + " has left the chat!");
                    synchronized (clients) {
                        clients.remove(out);
                        usernames.remove(username);
                    }
                    // Broadcast updated username list
                    broadcast("USERLIST:" + String.join(",", usernames));
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
            }
        }
    }

    // Send message to all connected clients
    private static void broadcast(String message) {
        synchronized (clients) {
            for (PrintWriter client : clients) {
                client.println(message);
            }
        }
    }
}