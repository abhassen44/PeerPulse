package backend.router;

import backend.services.UniversityService;
import backend.services.UserService;

import java.text.DateFormat;
import java.util.Date;

public class CommandRouter
{
	static UserService userService = new UserService();
	static UniversityService universityService = new UniversityService();

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
			case "GET_USER":
				return userService.getProfile(parts[1]);
			case "GET_RANDOM_PROFILE":
				return userService.getRandomProfile();
			case "GET_USER_BY_UNIVERSITY":
				return userService.getRandomUserByUniversity(parts[1]);
			case "GET_UNIVERSITY":
				return universityService.getUniversity(parts[1]);
			case "ADD_UNIVERSITY":
				return universityService.addUniversity(parts[1], parts[2]);
			case "ADD_ADMIN_USER":
				return universityService.addAdminUser(parts[1], parts[2]);
			case "DELETE_UNIVERSITY":
				return universityService.deleteUniversity(parts[1]);
			default:
				return "ERROR|Unknown command";
		}
	}
}
