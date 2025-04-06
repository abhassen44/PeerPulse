package backend.services;

import backend.DAO.TransactionsDAO;
import backend.DAO.UserDAO;
import backend.models.Transactions;
import backend.models.User;

import java.sql.SQLException;
import java.util.Date;

public class UserService
{
	private UserDAO userDAO = new UserDAO();
	private TransactionsDAO transactionsDAO = new TransactionsDAO();

	public String login(String username, String password)
	{
		boolean valid = userDAO.validateCredentials(username, password);
		return valid ? "SUCCESS|Login successful" : "ERROR|Invalid credentials";
	}

	public String register(String username, String password, String name, String dob, String university, String sex)
	{
//		try
//		{
//			// TODO: complete it
//		}
//		catch (SQLException e)
//		{
//			System.err.println("Error registering user: " + e.getMessage());
//			e.printStackTrace();
//			return "ERROR|Registration failed";
//		}

		return null;
	}

//	public void upvoteUser(String sender, String receiver)
//	{
//		try
//		{
//			userDAO.updateLikes(receiver, 1);
//			transactionsDAO.insert(new Transactions(sender, receiver, 1, new Date()));
//		}
//		catch (SQLException e)
//		{
//			System.err.println("Error upvoting user: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}
}
