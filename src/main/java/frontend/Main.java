package frontend;

import javax.swing.*;

public class Main implements frontend.ActionListener
{
	DisplayFrame displayFrame;

	String currentSessionUserName;

	Main()
	{
		this.displayFrame = new DisplayFrame();
		showLoginPanel();
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
		this.displayFrame.getContentPane().removeAll();
		this.displayFrame.revalidate();
		HomePanel homePanel = new HomePanel(this);
		this.displayFrame.add(homePanel);
		displayFrame.revalidate(); // Why do I have to revalidate image here, but not all other components everywhere else?
		this.displayFrame.repaint();
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
	}

	@Override
	public void onActionPerformed(String action, String username, String password)
	{
		System.out.println(action + " " + username + " " + password);

		if (action.equals("Log In"))
		{
			System.out.println("Log In");
			// validate username and password

			boolean valid = true;

			if (!valid)
			{
				showErrorMessagePanel("Invalid Username or Password");
			}
			else
			{
				currentSessionUserName = username;
				showHomePanel();
			}
		}
	}

	@Override
	public void onActionPerformed(String action, String username, String password, String confirmPassword, String securityQuestion, String securityAnswer)
	{
		System.out.println(action + " " + username + " " + password + " " + confirmPassword + " " + securityQuestion + " " + securityAnswer);

		if (action.equals("Sign Up"))
		{
			System.out.println("Sign Up");

			// check for availability of username

			// hash password

			// check for password match

			// update in database

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

			// TODO: Remove Later
			if (username.equals("Anvesh_2095"))
				showErrorMessagePanel("Admin");
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
		}
		else if (action.equals("downvote"))
		{
			System.out.println("downvote");
		}
		else if (action.equals("next"))
		{
			System.out.println("next");
		}
		else if (action.equals("deleteAccount"))
		{
			System.out.println("deleteAccount");
			showDialogBox("Do you want to delete your account?");
		}
	}
}
