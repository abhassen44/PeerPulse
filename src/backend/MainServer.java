package backend;

import java.io.*;
import java.net.*;

import backend.router.CommandRouter;

public class MainServer
{
	public static void main(String[] args)
	{
		int port = 12345; // default port
		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			System.out.println("Server started on port " + port);
			while (true)
			{
				Socket clientSocket = serverSocket.accept();
				new Thread(() -> handleClient(clientSocket)).start();
			}
		}
		catch (IOException e)
		{
			System.err.println("Error starting server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void handleClient(Socket clientSocket)
	{
		try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true))
		{
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				System.out.println("Received: " + inputLine);

				String response = CommandRouter.route(inputLine);
				out.println(response);
			}
		}
		catch (IOException e)
		{
			System.err.println("Error handling client: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
