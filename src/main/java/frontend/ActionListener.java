package frontend;

public interface ActionListener
{
	void onActionPerformed(String action);

	void onActionPerformed(String action, String username, String password);

	void onActionPerformed(String action, String username, String password, String confirmPassword, String securityQuestion, String securityAnswer);

	void onActionPerformed(String action, String username, String securityAnswer, String newPassword, String confirmPassword);

	void onActionPerformed(String action, String username);

	void onActionPerformed(String action, String username, String password, String confirmPassword, String securityQuestion, String securityAnswer, String name, String dob, String university, String sex);
}
