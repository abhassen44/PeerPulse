package backend.services;

import backend.DAO.LogDAO;
import backend.DAO.TransactionsDAO;
import backend.DAO.UserDAO;
import backend.models.Log;
import backend.models.Transactions;

import java.util.Date;

public class TransactionsService
{
	TransactionsDAO transactionsDAO = new TransactionsDAO();
	UserDAO userDAO = new UserDAO();
	LogDAO logDAO = new LogDAO();

	public String upvote(String sender, String receiver)
	{
		if (!userDAO.isUserNameExists(sender))
		{
			return "ERROR|Sender does not exist";
		}
		if (!userDAO.isUserNameExists(receiver))
		{
			return "ERROR|Receiver does not exist";
		}

		try
		{
			if (sender.equals(receiver))
			{
				return "ERROR|Cannot upvote yourself";
			}
			if (!transactionsDAO.hasTransaction(sender, receiver))
			{
				userDAO.updateLikes(receiver, 1);
				transactionsDAO.insert(new Transactions(sender, receiver, 1, new Date()));
			}
			else
			{
				Transactions t = transactionsDAO.findTransaction(sender, receiver);
				if (t.getAmount() == 1)
				{
					return "ERROR|Already upvoted";
				}
				else if (t.getAmount() == -1)
				{
					userDAO.updateLikes(receiver, 2);
					t.setAmount(t.getAmount() + 2);
					t.setDate(new Date());
					transactionsDAO.update(t);
				}
			}
			Log log = new Log(receiver, "You have been upvoted by someone", new java.sql.Date(new java.util.Date().getTime()));
			logDAO.insert(log);
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
		if (!userDAO.isUserNameExists(sender))
		{
			return "ERROR|Sender does not exist";
		}
		if (!userDAO.isUserNameExists(receiver))
		{
			return "ERROR|Receiver does not exist";
		}

		try
		{
			if (sender.equals(receiver))
			{
				return "ERROR|Cannot downvote yourself";
			}
			if (!transactionsDAO.hasTransaction(sender, receiver))
			{
				userDAO.updateLikes(receiver, -1);
				transactionsDAO.insert(new Transactions(sender, receiver, -1, new Date()));
			}
			else
			{
				Transactions t = transactionsDAO.findTransaction(sender, receiver);
				if (t.getAmount() == -1)
				{
					return "ERROR|Already downvoted";
				}
				else if (t.getAmount() == 1)
				{
					userDAO.updateLikes(receiver, -2);
					t.setAmount(t.getAmount() - 2);
					t.setDate(new Date());
					transactionsDAO.update(t);
				}
			}
			Log log = new Log(receiver, "You have been downvoted by someone", new java.sql.Date(new java.util.Date().getTime()));
			logDAO.insert(log);
			return "SUCCESS|Downvote recorded";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "ERROR|Downvote failed";
		}
	}
}
