import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        try (
            Socket socket = new Socket(hostname, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to server at " + hostname + ":" + port);

            String userInput;
            while (true) {
                System.out.print("Enter message (or 'exit' to quit): ");
                userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                out.println(userInput);
                String serverResponse = in.readLine();
                System.out.println("Server response: " + serverResponse);
            }
            System.out.println("Disconnected from server.");

        } catch (IOException e) {
            System.err.println("Could not connect to server or an I/O error occurred: " + e.getMessage());
        }
    }
}