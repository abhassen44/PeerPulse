package backend.services;

import backend.DAO.TransactionsDAO;
import backend.DAO.UserAuthDAO;
import backend.DAO.UserDAO;
import backend.models.Transactions;
import backend.models.User;
import backend.models.UserAuth;
import backend.session.SessionManager;

import java.sql.SQLException;
import java.util.Date;

public class UserService
{
	private UserDAO userDAO = new UserDAO();
	private TransactionsDAO transactionsDAO = new TransactionsDAO();
	private UserAuthDAO userAuthDAO = new UserAuthDAO();

	public String login(String username, String password)
	{
		if (SessionManager.isLoggedIn(username))
		{
			return "ERROR|User already logged in";
		}

		try
		{
			if (userDAO.validateCredentials(username, password))
			{
				SessionManager.login(username);
				return "SUCCESS|Login successful";
			}
			else
			{
				return "ERROR|Invalid credentials";
			}
		}
		catch (SQLException e)
		{
			System.err.println("Error validating credentials: " + e.getMessage());
			e.printStackTrace();
			return "ERROR|Login failed";
		}
	}

	public String register(String username, String password, String name, Date dob, String university, char sex, String securityQuestion, String securityAnswer)
	{
		User user = new User(username, name, dob, university, 0, sex, new Date());
		UserAuth userAuth = new UserAuth(username, password, securityQuestion, securityAnswer);
		try
		{
			userDAO.insert(user);
			userAuthDAO.insert(userAuth);
			userDAO.incrementUniversityStudentCount(university); // Increment university student count
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
					t.setAmount(t.getAmount() + 1);
					t.setDate(new Date());
					transactionsDAO.update(t);
				}
			}
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
					t.setAmount(t.getAmount() - 1);
					t.setDate(new Date());
					transactionsDAO.update(t);
				}
			}
			return "SUCCESS|Downvote recorded";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "ERROR|Downvote failed";
		}
	}

	public String getProfile(String username)
	{
		try
		{
			User user = userDAO.findByUsername(username);
			if (user == null)
			{
				return "ERROR|User not found";
			}

			return String.join("|",
					"SUCCESS",
					user.getUsername(),
					user.getName(),
					user.getDateOfBirth().toString(),
					user.getUniversity(),
					String.valueOf(user.getLikes()),
					String.valueOf(user.getSex()),
					user.getDateJoined().toString()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "ERROR|Could not retrieve user profile";
		}
	}

	public String getRandomProfile()
	{
		try
		{
			User user = userDAO.getRandomUser();
			if (user == null)
			{
				return "ERROR|No random user found";
			}

			return String.join("|",
					"SUCCESS",
					user.getUsername(),
					user.getName(),
					user.getDateOfBirth().toString(),
					user.getUniversity(),
					String.valueOf(user.getLikes()),
					String.valueOf(user.getSex()),
					user.getDateJoined().toString()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "ERROR|Could not retrieve random user profile";
		}
	}
}
