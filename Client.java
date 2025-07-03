import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("Connected to server.");

            while (true) {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Add Student");
                System.out.println("2. View All");
                System.out.println("3. Search by ID");
                System.out.println("4. Delete by ID");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Age: ");
                    String age = scanner.nextLine();
                    System.out.print("Course: ");
                    String course = scanner.nextLine();
                    out.println("ADD|" + id + "|" + name + "|" + age + "|" + course);
                } else if (choice.equals("2")) {
                    out.println("VIEW");
                } else if (choice.equals("3")) {
                    System.out.print("Enter ID to search: ");
                    String id = scanner.nextLine();
                    out.println("SEARCH|" + id);
                } else if (choice.equals("4")) {
                    System.out.print("Enter ID to delete: ");
                    String id = scanner.nextLine();
                    out.println("DELETE|" + id);
                } else if (choice.equals("5")) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid option.");
                    continue;
                }

                // Read server response
                String response;
                while ((response = in.readLine()) != null) {
                    if (response.isEmpty()) break;
                    System.out.println(response);
                    if (!in.ready()) break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
