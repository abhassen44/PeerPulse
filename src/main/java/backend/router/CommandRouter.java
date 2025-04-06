package backend.router;

import backend.services.UserService;

import java.text.DateFormat;
import java.util.Date;

public class CommandRouter
{
	static UserService userService = new UserService();

	public static String route(String command)
	{
		String[] parts = command.split("\\|");
		String action = parts[0];

		switch (action)
		{
			case "LOGIN":
				return userService.login(parts[1], parts[2]);
			case "REGISTER":
				return userService.register(parts[1], parts[2], parts[3], new Date(parts[4]), parts[5], parts[6].charAt(0), parts[7], parts[8]);
			case "UPVOTE":
				return userService.upvote(parts[1], parts[2]);
			case "DOWNVOTE":
				return userService.downvote(parts[1], parts[2]);
			default:
				return "ERROR|Unknown command";
		}
	}
}
