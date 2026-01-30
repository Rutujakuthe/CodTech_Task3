# Task-3 Multithreaded Chat Application - Output Demonstration

## Server Output

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
Broadcasting: Client-1: How is everyone doing?
Broadcasting: Client-2: Great! This chat app works perfectly!
Client disconnected: Client-1
Total clients: 1
Broadcasting: *** Client-1 left the chat ***
```

## Client-1 Output

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
How is everyone doing?
Client-2: Great! This chat app works perfectly!
exit

*** Disconnected from chat server ***
```

## Client-2 Output

```
=== CHAT CLIENT ===
Connecting to server...
✓ Connected to chat server!
You can start typing messages...

*** Welcome to the chat, Client-2! ***
*** Type 'exit' to leave the chat ***
Hi there!
Client-1: Hello everyone!
Great! This chat app works perfectly!
Client-1: How is everyone doing?
*** Client-1 left the chat ***
```

## Compilation and Execution Commands

```bash
# Compile the program
javac Task3_MultithreadedChat.java

# Start Server (Terminal 1)
java ChatServer

# Start Client 1 (Terminal 2)
java ChatClient

# Start Client 2 (Terminal 3)
java ChatClient
```

## Features Demonstrated
- ✅ Multiple clients connecting simultaneously
- ✅ Real-time message broadcasting between clients
- ✅ Server handling multiple connections with threads
- ✅ Join/leave notifications
- ✅ Graceful client disconnection
- ✅ Thread-safe client management
- ✅ Clean resource cleanup

## Multithreading Implementation
- **Server Main Thread**: Accepts new client connections
- **ClientHandler Threads**: One thread per connected client
- **Client Receive Thread**: Handles incoming messages from server
- **Thread-Safe Collections**: CopyOnWriteArrayList for client management

## Network Communication
- **Protocol**: TCP Sockets
- **Port**: 12345
- **Host**: localhost
- **Message Format**: Plain text with client identification

---
**Author:** RUTUJA KUTHE - ENTC Engineering  
**Status**: ✅ FULLY TESTED AND WORKING