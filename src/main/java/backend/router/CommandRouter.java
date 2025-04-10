package backend.router;

import backend.services.LogService;
import backend.services.TransactionsService;
import backend.services.UniversityService;
import backend.services.UserService;
import backend.session.SessionManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandRouter
{
	static UserService userService = new UserService();
	static UniversityService universityService = new UniversityService();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static TransactionsService transactionsService = new TransactionsService();
	static LogService logService = new LogService();

	public static String route(String command) {
		String[] parts = command.split("\\|");
		String action = parts[0];

		// Handle session validation for specific commands
		if (action.equals("UPVOTE") || action.equals("DOWNVOTE") || action.equals("LOGOUT") || action.equals("GET_PROFILE") || action.equals("DELETE_ACCOUNT") || action.equals("GET_USER_BY_UNIVERSITY") || action.equals("GET_LOGS"))
		{
			if (parts.length > 1 && !SessionManager.isAuthorized(parts[1]))
			{
				return "ERROR|Session expired. Please log in again.";
			}
		}

		switch (action)
		{
			case "LOGIN":
				return userService.login(parts[1], parts[2]);
			case "REGISTER":
				try
				{
					Date birthDate = dateFormat.parse(parts[4]);
					return userService.register(parts[1], parts[2], parts[3], birthDate, parts[5], parts[6].charAt(0), parts[7], parts[8]);
				}
				catch (ParseException e)
				{
					return "ERROR|Invalid date format. Please use yyyy-MM-dd format.";
				}
			case "UPVOTE":
				return transactionsService.upvote(parts[2], parts[3]);
			case "DOWNVOTE":
				return transactionsService.downvote(parts[2], parts[3]);
			case "GET_PROFILE":
				return userService.getProfile(parts[2]);
			case "GET_RANDOM_PROFILE":
				return userService.getRandomProfile();
			case "GET_USER_BY_UNIVERSITY":
				return userService.getRandomUserSameUniversity(parts[2]);
			case "GET_UNIVERSITY":
				return universityService.getUniversity(parts[1]);
			case "ADD_UNIVERSITY":
				return universityService.addUniversity(parts[1], parts[2]);
			case "ADD_ADMIN_USER":
				return universityService.addAdminUser(parts[1], parts[2]);
			case "DELETE_UNIVERSITY":
				return universityService.deleteUniversity(parts[1]);
			case "LOGOUT":
				return userService.logout(parts[2]);
			case "DELETE_ACCOUNT":
				return userService.deleteAccount(parts[2], parts[3]);
			case "GET_LOGS":
				return logService.getLogs(parts[2]);
			default:
				return "ERROR|Unknown command";
		}
	}
}
