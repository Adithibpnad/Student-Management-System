# Student Management System (Socket Programming in Java)

This project implements a simple **Student Management System** using Java socket programming. It allows multiple clients to connect to a server and perform CRUD operations (Add, View, Search, Delete) on student records. All communication between the client and server happens via TCP sockets.

---

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [How It Works](#how-it-works)
- [Usage](#usage)
  - [Prerequisites](#prerequisites)
  - [Compiling the Project](#compiling-the-project)
  - [Running the Server](#running-the-server)
  - [Running the Client(s)](#running-the-clients)
- [Code Structure](#code-structure)
- [Protocol](#protocol)
- [Sample Interaction](#sample-interaction)
- [Notes](#notes)
- [License](#license)

---

## Features

- Add a new student (ID, Name, Age, Course)
- View all students
- Search for a student by ID
- Delete a student by ID
- Multiple clients can connect and interact concurrently (threaded server)
- Simple text-based client UI

---

## Architecture

- **Server:** Listens on a specified port, maintains a shared list of students, and spawns a new thread for each connecting client.
- **Client:** Connects to the server and provides a menu-driven interface for user commands.
- **ClientHandler:** Handles each client connection in a separate thread, processes commands, and manipulates the shared student list.

```
            [Client 1]         [Client 2]
                |                  |
                +--------+---------+
                         |
                    [ Server ]
                         |
                   (Shared List)
```

---

## How It Works

1. **Start the Server:** It waits for incoming client connections.
2. **Client connects:** The client displays a menu and lets the user select actions.
3. **Command Sent:** The client sends formatted commands to the server over the socket.
4. **Server Processes:** The server parses commands, updates the student list, and sends responses.
5. **Repeat:** Multiple clients can use the system in parallel.

---

## Usage

### Prerequisites

- Java (JDK 8 or higher) must be installed and `javac`/`java` must be in your PATH.

### Compiling the Project

Open a terminal in the project directory and run:

```sh
javac *.java
```

This will compile all Java files and produce `.class` files.

### Running the Server

```sh
java Server
```

You should see:

```
Server starting on port 1234...
```

### Running the Clients

In separate terminal windows (can run multiple clients):

```sh
java Client
```

You should see:

```
Connected to server.

--- Menu ---
1. Add Student
2. View All
3. Search by ID
4. Delete by ID
5. Exit
Choose an option:
```

---

## Code Structure

- `Server.java`  
  Starts the server, accepts client connections, and creates a new `ClientHandler` thread for each.
- `ClientHandler.java`  
  Handles client requests on a separate thread, processes commands, and manipulates the shared list of students.
- `Student.java`  
  Simple POJO (Plain Old Java Object) representing a student entity.
- `Client.java`  
  Provides a menu-driven user interface, sends commands to the server, and displays server responses.

---

## Protocol

Client and server communicate using simple string commands separated by the `|` character:

- **Add Student:**  
  `ADD|<id>|<name>|<age>|<course>`
- **View All Students:**  
  `VIEW`
- **Search by ID:**  
  `SEARCH|<id>`
- **Delete by ID:**  
  `DELETE|<id>`

The server responds with plain text messages or lists of students.

---

## Sample Interaction

**Client UI:**

```
--- Menu ---
1. Add Student
2. View All
3. Search by ID
4. Delete by ID
5. Exit
Choose an option: 1
ID: 101
Name: Alice
Age: 20
Course: Math
Student added successfully.

--- Menu ---
1. Add Student
2. View All
3. Search by ID
4. Delete by ID
5. Exit
Choose an option: 2
ID: 101, Name: Alice, Age: 20, Course: Math
```

---

## Notes

- The student list is **not persisted**; restarting the server clears all records.
- No authentication is implemented.
- The server currently does **not** prevent duplicate student IDs.
- Clients must use unique IDs to avoid ambiguity.
- The system is for educational demonstration purposes.

---

## License

This project is provided for educational purposes. Feel free to use and modify as needed.
