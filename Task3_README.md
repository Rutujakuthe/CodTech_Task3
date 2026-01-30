# INTERNSHIP TASK-3: MULTITHREADED CHAT APPLICATION

**Student:** RUTUJA KUTHE - ENTC Engineering  
**Company:** CODTECH IT SOLUTIONS  
**Task:** Client-Server Chat Application using Java Sockets and Multithreading

## Project Overview
A real-time chat application that allows multiple clients to connect to a server and communicate simultaneously using Java sockets and multithreading.

## Features
- ✅ Multiple clients can connect simultaneously
- ✅ Real-time message broadcasting
- ✅ Thread-safe client management
- ✅ Automatic join/leave notifications
- ✅ Graceful disconnect handling
- ✅ Server handles client connections with individual threads

## Architecture
- **ChatServer** - Main server handling multiple client connections
- **ClientHandler** - Manages individual client connections (implements Runnable)
- **ChatClient** - Client application for users to connect and chat

## How to Run

### Step 1: Compile
```bash
javac Task3_MultithreadedChat.java
```

### Step 2: Start Server (Terminal 1)
```bash
java ChatServer
```

### Step 3: Start Clients (Separate Terminals)
```bash
java ChatClient
```

## Sample Server Output
```
=== MULTITHREADED CHAT SERVER ===
Server starting on port 12345...
✓ Server started successfully!
Waiting for client connections...

New client connected: Client-1
Total clients: 1
Broadcasting: *** Client-1 joined the chat ***
New client connected: Client-2
Total clients: 2
Broadcasting: *** Client-2 joined the chat ***
Broadcasting: Client-1: Hello everyone!
Broadcasting: Client-2: Hi there!
```

## Sample Client Output
```
=== CHAT CLIENT ===
Connecting to server...
✓ Connected to chat server!
You can start typing messages...

*** Welcome to the chat, Client-1! ***
*** Type 'exit' to leave the chat ***
*** Client-2 joined the chat ***
Hello everyone!
Client-2: Hi there!
```

## Technical Implementation

### Server Features:
- Uses `ServerSocket` on port 12345
- Maintains thread-safe client list with `CopyOnWriteArrayList`
- Creates new thread for each client connection
- Broadcasts messages to all connected clients
- Handles client disconnections gracefully

### Client Features:
- Connects to server using `Socket`
- Separate thread for receiving messages
- Main thread handles user input
- Automatic reconnection handling
- Clean disconnect with "exit" command

### Multithreading:
- **Server Thread**: Accepts new client connections
- **Client Handler Threads**: One per connected client
- **Client Receive Thread**: Handles incoming messages
- **Thread-Safe Operations**: Using synchronized collections

## Key Classes

### ChatServer
- Main server class with static client management
- Handles new connections and message broadcasting
- Port: 12345

### ClientHandler (implements Runnable)
- Manages individual client communication
- Runs in separate thread for each client
- Handles message relay and disconnection

### ChatClient
- Client-side application
- Dual-threaded: input handling + message receiving
- Connects to localhost:12345

## Commands
- **Type any message**: Broadcast to all clients
- **Type "exit"**: Disconnect from chat
- **Server shutdown**: Ctrl+C to stop server

## Error Handling
- Network connection failures
- Client disconnection handling
- Resource cleanup (sockets, streams)
- Thread-safe operations

---
**Author:** RUTUJA KUTHE - ENTC Engineering