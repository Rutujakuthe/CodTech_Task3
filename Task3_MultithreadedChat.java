import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * INTERNSHIP TASK-3: MULTITHREADED CHAT APPLICATION
 * Student: RUTUJA KUTHE - ENTC Engineering
 * Company: CODTECH IT SOLUTIONS
 * Client-Server Chat Application using Java Sockets and Multithreading
 */

// ChatServer class
class ChatServer {
    private static final int PORT = 12345;
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private static int clientCounter = 0;
    
    public static void main(String[] args) {
        System.out.println("=== MULTITHREADED CHAT SERVER ===");
        System.out.println("Server starting on port " + PORT + "...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("✓ Server started successfully!");
            System.out.println("Waiting for client connections...\n");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCounter++;
                
                System.out.println("New client connected: Client-" + clientCounter);
                System.out.println("Total clients: " + (clients.size() + 1));
                
                ClientHandler clientHandler = new ClientHandler(clientSocket, "Client-" + clientCounter);
                clients.add(clientHandler);
                
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    
    public static void broadcastMessage(String message, ClientHandler sender) {
        System.out.println("Broadcasting: " + message);
        
        for (ClientHandler client : clients) {
            if (client != sender && client.isConnected()) {
                client.sendMessage(message);
            }
        }
    }
    
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected: " + client.getClientName());
        System.out.println("Total clients: " + clients.size());
        
        broadcastMessage("*** " + client.getClientName() + " left the chat ***", client);
    }
}

// ClientHandler class
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private boolean connected;
    
    public ClientHandler(Socket socket, String name) {
        this.clientSocket = socket;
        this.clientName = name;
        this.connected = true;
        
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            sendMessage("*** Welcome to the chat, " + clientName + "! ***");
            sendMessage("*** Type 'exit' to leave the chat ***");
            
            ChatServer.broadcastMessage("*** " + clientName + " joined the chat ***", this);
            
        } catch (IOException e) {
            System.err.println("Error setting up client handler: " + e.getMessage());
            connected = false;
        }
    }
    
    @Override
    public void run() {
        try {
            String inputLine;
            
            while (connected && (inputLine = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(inputLine.trim())) {
                    break;
                }
                
                String message = clientName + ": " + inputLine;
                ChatServer.broadcastMessage(message, this);
            }
            
        } catch (IOException e) {
            System.err.println("Error handling client " + clientName + ": " + e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    public void sendMessage(String message) {
        if (connected && out != null) {
            out.println(message);
        }
    }
    
    public void disconnect() {
        connected = false;
        
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client resources: " + e.getMessage());
        }
        
        ChatServer.removeClient(this);
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public boolean isConnected() {
        return connected;
    }
}

// ChatClient class
class ChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;
    private boolean connected;
    
    public ChatClient() {
        scanner = new Scanner(System.in);
        connected = false;
    }
    
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
    
    public void start() {
        System.out.println("=== CHAT CLIENT ===");
        System.out.println("Connecting to server...");
        
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
            
            System.out.println("✓ Connected to chat server!");
            System.out.println("You can start typing messages...\n");
            
            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();
            
            handleUserInput();
            
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            System.err.println("Make sure the server is running on " + SERVER_HOST + ":" + SERVER_PORT);
        } finally {
            disconnect();
        }
    }
    
    private void handleUserInput() {
        while (connected) {
            try {
                String message = scanner.nextLine();
                
                if (message == null || "exit".equalsIgnoreCase(message.trim())) {
                    break;
                }
                
                if (!message.trim().isEmpty()) {
                    out.println(message);
                }
                
            } catch (Exception e) {
                System.err.println("Error sending message: " + e.getMessage());
                break;
            }
        }
    }
    
    private void receiveMessages() {
        try {
            String message;
            while (connected && (message = in.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            if (connected) {
                System.err.println("Error receiving messages: " + e.getMessage());
            }
        }
    }
    
    private void disconnect() {
        connected = false;
        
        try {
            if (out != null) {
                out.println("exit");
                out.close();
            }
            if (in != null) in.close();
            if (socket != null) socket.close();
            if (scanner != null) scanner.close();
        } catch (IOException e) {
            System.err.println("Error during disconnect: " + e.getMessage());
        }
        
        System.out.println("\n*** Disconnected from chat server ***");
    }
}