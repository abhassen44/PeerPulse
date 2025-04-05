package backend.services;

import backend.DAO.TransactionsDAO;
import backend.DAO.UserDAO;
import backend.models.Transactions;

import java.sql.SQLException;
import java.util.Date;

public class UserService
{
	private UserDAO userDAO = new UserDAO();
	private TransactionsDAO transactionsDAO = new TransactionsDAO();

	public void upvoteUser(String sender, String receiver)
	{
		try
		{
			userDAO.updateLikes(receiver, 1);
			transactionsDAO.insert(new Transactions(sender, receiver, 1, new Date()));
		}
		catch (SQLException e)
		{
			System.err.println("Error upvoting user: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
