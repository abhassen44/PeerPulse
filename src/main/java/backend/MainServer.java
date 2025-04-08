package backend;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import backend.services.UserService;
import backend.session.SessionManager;

public class MainServer
{
	public static void main(String[] args)
	{
		int port = 12345;
		UserService userService = new UserService();

		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			System.out.println("Server listening on port " + port);

			while (true)
			{
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected: " + clientSocket.getInetAddress());

				new Thread(() ->
				{
					try
					{
						handleClient(clientSocket, userService);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}).start();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void handleClient(Socket clientSocket, UserService userService)
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
				String[] parts = inputLine.split("\\|");

				String action = parts[0];
				String response;

				switch (action.toUpperCase())
				{
					case "REGISTER":
						if (parts.length < 9)
						{
							response = "ERROR|Invalid REGISTER format";
							break;
						}

						String username = parts[1];
						String password = parts[2];
						String name = parts[3];
						String dobStr = parts[4];
						String university = parts[5];
						char sex = parts[6].charAt(0);
						String securityQuestion = parts[7];
						String securityAnswer = parts[8];

						try
						{
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date dob = sdf.parse(dobStr);
							response = userService.register(username, password, name, dob, university, sex, securityQuestion, securityAnswer);
						} catch (ParseException e)
						{
							response = "ERROR|Invalid date format. Use yyyy-MM-dd";
						}
						break;

					case "LOGIN":
						if (parts.length < 3)
						{
							response = "ERROR|Invalid LOGIN format";
							break;
						}
						response = userService.login(parts[1], parts[2]);
						break;

					case "UPVOTE":
						if (parts.length < 3)
						{
							response = "ERROR|Invalid UPVOTE format";
							break;
						}
						if (!SessionManager.isAuthorized(parts[1]))
						{
							response = "ERROR|Session expired. Please log in again.";
							break;
						}
						response = userService.upvote(parts[1], parts[2]);
						break;

					case "DOWNVOTE":
						if (parts.length < 3)
						{
							response = "ERROR|Invalid DOWNVOTE format";
							break;
						}
						if (!SessionManager.isAuthorized(parts[1]))
						{
							response = "ERROR|Session expired. Please log in again.";
							break;
						}
						response = userService.downvote(parts[1], parts[2]);
						break;

					case "LOGOUT":
						if (parts.length < 2)
						{
							response = "ERROR|Invalid LOGOUT format";
							break;
						}
						SessionManager.logout(parts[1]);
						response = "SUCCESS|Logged out";
						break;

					case "GET_PROFILE":
					if (parts.length < 2) {
						response = "ERROR|Missing username";
						break;
					}
					String profileUser = parts[1];
					if (!SessionManager.isAuthorized(profileUser)) {
						response = "ERROR|Session expired. Please log in again.";
						break;
					}
					response = userService.getProfile(profileUser);
					break;

					case "GET_RANDOM_PROFILE":
						response = userService.getRandomProfile();
						break;

					default:
						response = "ERROR|Unknown command";
						break;
				}

				out.println(response);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
