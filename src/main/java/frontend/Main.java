package frontend;

import backend.models.User;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main implements frontend.ActionListener
{
	DisplayFrame displayFrame;

	String currentSessionUserName;
	String currentHashedPassword;

	String hostname = "localhost";
	int port = 12345;

	Socket socket;
	BufferedReader in;
	PrintWriter out;

	UUID uuid;

	Main()
	{
		try
		{
			socket = new Socket(hostname, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
			System.out.println("Connected to server at " + hostname + ":" + port);

			this.displayFrame = new DisplayFrame();
			showLoginPanel();

			// Add WindowListener to handle logout on exit
			displayFrame.addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowClosing(WindowEvent e)
				{
					logoutOnExit();
				}
			});
		}
		catch (Exception e)
		{
			System.err.println("Could not connect to server or an I/O error occurred: " + e.getMessage());
			System.exit(1);
		}
	}
	public static void main(String[] args)
	{
		new Main();
	}

	void showLoginPanel()
	{
		this.displayFrame.getContentPane().removeAll();
		this.displayFrame.revalidate();
		LogInPanel logInPanel = new LogInPanel(this);
		this.displayFrame.add(logInPanel);
		// displayFrame.revalidate();
		this.displayFrame.repaint();
	}

	void showSignUpPanel()
	{
		this.displayFrame.getContentPane().removeAll();
		this.displayFrame.revalidate();
		SignUpPanel signUpPanel = new SignUpPanel(this);
		this.displayFrame.add(signUpPanel);
		// displayFrame.revalidate();
		this.displayFrame.repaint();
	}

	void showForgotPasswordPanel()
	{
		this.displayFrame.getContentPane().removeAll();
		this.displayFrame.revalidate();
		ForgotPasswordPanel forgotPasswordPanel = new ForgotPasswordPanel(this);
		this.displayFrame.add(forgotPasswordPanel);
		// displayFrame.revalidate();
		this.displayFrame.repaint();
	}

	void showErrorMessagePanel(String errorMessage)
	{
		this.displayFrame.getContentPane().removeAll();
		this.displayFrame.revalidate();
		ErrorMessageDisplayPanel errorMessageDisplayPanel = new ErrorMessageDisplayPanel(errorMessage, this);
		this.displayFrame.add(errorMessageDisplayPanel);
		// displayFrame.revalidate();
		this.displayFrame.repaint();
	}

	void showHomePanel()
	{
		// Request a random profile from the server
		out.println("GET_RANDOM_PROFILE");
		String serverResponse = null;
		try
		{
			serverResponse = in.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Could not read server response: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Server response: " + serverResponse);

		String[] parts = serverResponse.split("\\|");
		if (parts[0].equals("ERROR"))
		{
			showErrorMessagePanel(parts[1]);
			return;
		}

		// Extract profile details from the response
		String fetchedUsername = parts[1];
		String fetchedName = parts[2];
		int upvoteCount = 0;
		
		// Check if upvote count is included in the response
		if (parts.length > 5) {
			try {
				upvoteCount = Integer.parseInt(parts[5]);
			} catch (NumberFormatException e) {
				System.out.println("Error parsing upvote count: " + e.getMessage());
			}
		}

		this.displayFrame.getContentPane().removeAll();
		this.displayFrame.revalidate();
		HomePanel homePanel = new HomePanel(this, fetchedUsername, fetchedName, upvoteCount);
		this.displayFrame.add(homePanel);
		displayFrame.revalidate(); // Why do I have to revalidate image here, but not all other components everywhere else?
//		this.displayFrame.repaint();
	}

	void showDialogBox(String message)
	{
		JOptionPane.showMessageDialog(this.displayFrame, message);
	}

	@Override
	public void onActionPerformed(String action)
	{
		System.out.println(action);

		if (action.equals("Navigate Log In"))
		{
			System.out.println("Navigate Log In");
			showLoginPanel();
		}
		else if (action.equals("Navigate Sign Up"))
		{
			System.out.println("Navigate Sign Up");
			showSignUpPanel();
		}
		else if (action.equals("Navigate Forgot Password"))
		{
			System.out.println("Navigate Forgot Password");
			showForgotPasswordPanel();
		}
		else if (action.equals("next"))
		{
			System.out.println("next");
			showHomePanel(); // Refresh with next profile
		}
		else if (action.equals("exit"))
		{
			System.out.println("exit");
			logoutOnExit();
			System.exit(0);
		}
		else if (action.equals("deleteAccount"))
		{
			System.out.println("deleteAccount");
			showDialogBox("Do you want to delete your account?");
			out.println("DELETE_ACCOUNT|" + currentSessionUserName + currentHashedPassword);
			String serverResponse = null;
			try
			{
				serverResponse = in.readLine();
			}
			catch (IOException e)
			{
				System.out.println("Could not read server response: " + e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println("Server response: " + serverResponse);
			String[] parts = serverResponse.split("\\|");
			if (parts[0].equals("ERROR"))
			{
				showErrorMessagePanel(parts[1]);
			}
			else if (parts[0].equals("SUCCESS"))
			{
				System.out.println("Account deleted successfully");
				showDialogBox("Account deleted successfully");
				showLoginPanel(); // Redirect to login page
			}
		}
		else
		{
			System.out.println("Unknown action: " + action);
		}
	}

	@Override
	public void onActionPerformed(String action, String username, String password)
	{
		System.out.println(action + " " + username + " " + password);
		String hashedPassword = password; // TODO: Hash the password
		System.out.println("Hashed Password: " + hashedPassword);

		if (action.equals("Log In"))
		{
			System.out.println("Log In");
			// validate username and password

			out.println("LOGIN|" + username + "|" + hashedPassword);
			String serverResponse = null;
			try
			{
				serverResponse = in.readLine();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("Server response: " + serverResponse);
			String[] parts = serverResponse.split("\\|");

			if (parts[0].equals("ERROR"))
			{
				showErrorMessagePanel(parts[1]);
			}
			else if (parts[0].equals("SUCCESS"))
			{
				System.out.println("Login successful");
				currentSessionUserName = username;
				currentHashedPassword = hashedPassword;
				uuid = UUID.fromString(parts[2]);
				showHomePanel();
			}
		}
	}

	@Override
	public void onActionPerformed(String action, String username, String password, String confirmPassword, String securityQuestion, String securityAnswer)
	{

	}

	@Override
	public void onActionPerformed(String action, String username, String password, String confirmPassword, String securityQuestion, String securityAnswer, String name, String dob, String university, String sex)
	{
		System.out.println(action + " " + username + " " + password + " " + confirmPassword + " " + securityQuestion + " " + securityAnswer);

		if (action.equals("Sign Up"))
		{
			System.out.println("Sign Up");

			if (!password.equals(confirmPassword))
			{
				showErrorMessagePanel("Passwords do not match");
				return;
			}

			out.println("REGISTER|" + username + "|" + password + "|" + name + "|" + dob + "|" + university + "|" + sex + "|" + securityQuestion + "|" + securityAnswer);

			showLoginPanel();
		}
	}

	@Override
	public void onActionPerformed(String action, String username, String securityAnswer, String newPassword, String confirmPassword)
	{
		System.out.println(action + " " + username + " " + securityAnswer + " " + newPassword + " " + confirmPassword);

		if (action.equals("Submit"))
		{
			System.out.println("Submit");

			// check for username

			// check for security answer

			// check for password match

			// update in database

			showLoginPanel();
		}
	}

	@Override
	public void onActionPerformed(String action, String username)
	{
		System.out.println(action + " " + username);

		if (action.equals("viewProfile"))
		{
			System.out.println("viewProfile");
		}
		else if (action.equals("upvote"))
		{
			System.out.println("upvote");
			out.println("UPVOTE|" + uuid + "|" + currentSessionUserName + "|" + username);
			String serverResponse = null;
			try
			{
				serverResponse = in.readLine();
			}
			catch (IOException e)
			{
				System.out.println("Could not read server response: " + e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println("Server response: " + serverResponse);
			String[] parts = serverResponse.split("\\|");
			if (parts[0].equals("ERROR"))
			{
				// Check if the error is due to upvoting oneself
				if (parts[1].equals("Cannot upvote yourself") || parts[1].equals("Already upvoted"))
				{
					showDialogBox(parts[1]); // Show the error in a dialog box
				} else
				{
					showErrorMessagePanel(parts[1]); // Redirect to login for other errors
				}
			}
			else if (parts[0].equals("SUCCESS"))
			{
				System.out.println("Upvote successful");
				showDialogBox("Upvote successful");
				showHomePanel(); // Refresh with next profile
			}
		}
		else if (action.equals("downvote"))
		{
			System.out.println("downvote");
			out.println("DOWNVOTE|" + uuid + "|" + currentSessionUserName + "|" + username);
			String serverResponse = null;
			try
			{
				serverResponse = in.readLine();
			}
			catch (IOException e)
			{
				System.out.println("Could not read server response: " + e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println("Server response: " + serverResponse);
			String[] parts = serverResponse.split("\\|");
			if (parts[0].equals("ERROR"))
			{
				// Check if the error is due to downvoting oneself
				if (parts[1].equals("Cannot downvote yourself") || parts[1].equals("Already downvoted"))
				{
					showDialogBox(parts[1]); // Show the error in a dialog box
				}
				else
				{
					showErrorMessagePanel(parts[1]); // Redirect to login for other errors
				}
			}
			else if (parts[0].equals("SUCCESS"))
			{
				System.out.println("Downvote successful");
				showDialogBox("Downvote successful");
				showHomePanel(); // Refresh with next profile
			}
		}
		else if (action.equals("next"))
		{
			System.out.println("next");
			showHomePanel(); // Refresh with next profile
		}
		else if (action.equals("deleteAccount"))
		{
			System.out.println("deleteAccount");
			showDialogBox("Do you want to delete your account?");
			out.println("DELETE_ACCOUNT|" + currentSessionUserName + currentHashedPassword);
			String serverResponse = null;
			try
			{
				serverResponse = in.readLine();
			}
			catch (IOException e)
			{
				System.out.println("Could not read server response: " + e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println("Server response: " + serverResponse);
			String[] parts = serverResponse.split("\\|");
			if (parts[0].equals("ERROR"))
			{
				showErrorMessagePanel(parts[1]);
			}
			else if (parts[0].equals("SUCCESS"))
			{
				System.out.println("Account deleted successfully");
				showDialogBox("Account deleted successfully");
				showLoginPanel(); // Redirect to login page
			}
		}
	}


	private void logoutOnExit()
	{
		if (currentSessionUserName != null)
		{
			System.out.println("Logging out " + currentSessionUserName + " on exit");
			out.println("LOGOUT|" + uuid + "|" + currentSessionUserName);
			try
			{
				String serverResponse = in.readLine();
				System.out.println("Server response: " + serverResponse);
			} catch (IOException e)
			{
				System.out.println("Could not read server response: " + e.getMessage());
				e.printStackTrace();
			} finally
			{
				try
				{
					socket.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			try
			{
				socket.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

// TODO: upvote, downvote, logout, get profile requires token
