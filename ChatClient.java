import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    public ChatClient(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    public void start() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        
        Thread messageReceiver = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Connection lost");
            }
        });
        messageReceiver.start();
        
        String userMessage;
        while ((userMessage = userInput.readLine()) != null) {
            out.println(userMessage);
        }
        
        socket.close();
    }
    
    public static void main(String[] args) {
        try {
            ChatClient client = new ChatClient("127.0.0.1", 12345);
            client.start();
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}