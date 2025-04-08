package backend;

import backend.router.CommandRouter;
import backend.session.SessionManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String[] args) {
		int port = 12345;

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server listening on port " + port);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected: " + clientSocket.getInetAddress());

				new Thread(() -> {
					try {
						handleClient(clientSocket);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handleClient(Socket clientSocket)
	{
		try (
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
		)
		{
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				System.out.println("Received: " + inputLine);

				// Delegate command processing to CommandRouter
				String response = CommandRouter.route(inputLine);

				out.println(response);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}