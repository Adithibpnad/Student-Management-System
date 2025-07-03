import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private List<Student> students;

    public ClientHandler(Socket socket, List<Student> students) {
        this.socket = socket;
        this.students = students;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                String response = processCommand(line);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processCommand(String command) {
        String[] parts = command.split("\\|");
        String action = parts[0];

        switch (action.toUpperCase()) {
            case "ADD":
                if (parts.length != 5) return "ERROR: Invalid ADD format";
                String id = parts[1];
                String name = parts[2];
                int age = Integer.parseInt(parts[3]);
                String course = parts[4];
                students.add(new Student(id, name, age, course));
                return "Student added successfully.";
            case "VIEW":
                if (students.isEmpty()) return "No students.";
                StringBuilder sb = new StringBuilder();
                for (Student s : students) {
                    sb.append(s.toString()).append("\n");
                }
                return sb.toString();
            case "SEARCH":
                if (parts.length != 2) return "ERROR: Invalid SEARCH format";
                String searchId = parts[1];
                for (Student s : students) {
                    if (s.getId().equals(searchId)) {
                        return s.toString();
                    }
                }
                return "Student not found.";
            case "DELETE":
                if (parts.length != 2) return "ERROR: Invalid DELETE format";
                String deleteId = parts[1];
                for (Student s : students) {
                    if (s.getId().equals(deleteId)) {
                        students.remove(s);
                        return "Student deleted.";
                    }
                }
                return "Student not found.";
            default:
                return "ERROR: Unknown command.";
        }
    }
}
