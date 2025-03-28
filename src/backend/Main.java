package backend;

import java.io.IOException;
import java.net.ServerSocket;

public class Main
{
	public static int PORT = 8081;
	public static void main(String[] args)
	{

	}

	ServerSocket serverSocket;

	Main()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true)
			{
				new ClientHandler(serverSocket.accept()).start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
