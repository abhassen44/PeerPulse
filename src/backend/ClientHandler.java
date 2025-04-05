package backend;

import java.net.Socket;

public class ClientHandler
{
	private Socket socket;
	private String clientName;
	private String clientAddress;
	private int clientPort;

	public ClientHandler(Socket socket)
	{
		this.socket = socket;
		this.clientName = socket.getInetAddress().getHostName();
		this.clientAddress = socket.getInetAddress().getHostAddress();
		this.clientPort = socket.getPort();


	}

	public void start()
	{
		// start handling client requests
	}
}
