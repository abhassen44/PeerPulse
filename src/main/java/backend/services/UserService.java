package backend.services;

import backend.DAO.TransactionsDAO;
import backend.DAO.UserAuthDAO;
import backend.DAO.UserDAO;
import backend.models.Transactions;
import backend.models.User;
import backend.models.UserAuth;

import java.sql.SQLException;
import java.util.Date;

public class UserService
{
	private UserDAO userDAO = new UserDAO();
	private TransactionsDAO transactionsDAO = new TransactionsDAO();
	private UserAuthDAO userAuthDAO = new UserAuthDAO();

	public String login(String username, String password)
	{
		boolean valid = userDAO.validateCredentials(username, password);
		return valid ? "SUCCESS|Login successful" : "ERROR|Invalid credentials";
	}

	public String register(String username, String password, String name, Date dob, String university, char sex, String securityQuestion, String securityAnswer)
	{
		User user = new User(username, name, dob, university, 0, sex, new Date());
		UserAuth userAuth = new UserAuth(username, password, securityQuestion, securityAnswer);
		try
		{
			userDAO.insert(user);
			userAuthDAO.insert(userAuth);
			return "SUCCESS|Registration successful";
		}
		catch (SQLException e)
		{
			System.err.println("Error registering user: " + e.getMessage());
			e.printStackTrace();
			return "ERROR|Registration failed";
		}
	}

	public String upvote(String sender, String receiver)
	{
		try
		{
			userDAO.updateLikes(receiver, 1);
			transactionsDAO.insert(new Transactions(sender, receiver, 1, new Date()));
			return "SUCCESS|Upvote recorded";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "ERROR|Upvote failed";
		}
	}

	public String downvote(String sender, String receiver)
	{
		try
		{
			userDAO.updateLikes(receiver, -1);
			transactionsDAO.insert(new Transactions(sender, receiver, -1, new Date()));
			return "SUCCESS|Downvote recorded";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "ERROR|Downvote failed";
		}
	}
}
