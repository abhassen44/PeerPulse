package frontend;

import javax.swing.*;

public class SignUpPanel extends JPanel
{
	SignUpPanel(ActionListener actionListener)
	{
		setSize(800, 600);
		setVisible(true);

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(300, 150, 100, 30);
		add(usernameLabel);

		JTextField usernameField = new JTextField();
		usernameField.setBounds(400, 150, 100, 30);
		usernameField.setSize(200, 30);
		add(usernameField);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(300, 200, 100, 30);
		add(passwordLabel);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(400, 200, 100, 30);
		passwordField.setSize(200, 30);
		add(passwordField);

		JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
		confirmPasswordLabel.setBounds(250, 250, 150, 30);
		add(confirmPasswordLabel);

		JPasswordField confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(400, 250, 100, 30);
		confirmPasswordField.setSize(200, 30);
		add(confirmPasswordField);

		JLabel securityQuestionLabel = new JLabel("Security Question:");
		securityQuestionLabel.setBounds(250, 300, 150, 30);
		add(securityQuestionLabel);

		JTextField securityQuestionField = new JTextField(GeneralSavedData.securityQuestion);
		securityQuestionField.setBounds(400, 300, 100, 30);
		securityQuestionField.setSize(200, 30);
		add(securityQuestionField);

		JLabel securityAnswerLabel = new JLabel("Security Answer:");
		securityAnswerLabel.setBounds(260, 350, 150, 30);
		add(securityAnswerLabel);

		JTextField securityAnswerField = new JTextField();
		securityAnswerField.setBounds(400, 350, 100, 30);
		securityAnswerField.setSize(200, 30);
		add(securityAnswerField);

		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(300, 400, 100, 30);
		add(signUpButton);
		signUpButton.addActionListener((e) -> actionListener.onActionPerformed("Sign Up", usernameField.getText(), new String(passwordField.getPassword()), new String(confirmPasswordField.getPassword()), securityQuestionField.getText(), securityAnswerField.getText()));

		JButton logInButton = new JButton("Log In");
		logInButton.setBounds(450, 400, 100, 30);
		add(logInButton);
		logInButton.addActionListener((e) -> actionListener.onActionPerformed("Navigate Log In"));
	}
}