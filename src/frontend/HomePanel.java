package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class HomePanel extends JPanel
{
	public HomePanel(ActionListener actionListener)
	{
		// get a random person's profile

		try
		{
			InputStream imageStream = getClass().getResourceAsStream("/Images/DemoFemale.png");
			assert imageStream != null;
			BufferedImage image = ImageIO.read(imageStream);
			JLabel profilePicture = new JLabel(new ImageIcon(image));
			profilePicture.setBounds(5, 5, 100, 100);
			profilePicture.setSize(100, 100);
			add(profilePicture);

		} catch (IOException e)
		{
			e.printStackTrace();
		}

		String fetchedName = null;
		JLabel name = new JLabel("Name: " + fetchedName);
		name.setBounds(110, 5, 100, 20);
		add(name);

		String fetchedUsername = null;
		JLabel username = new JLabel("Username: " + fetchedUsername);
		username.setBounds(110, 25, 100, 20);
		add(username);

		JButton viewProfileButton = new JButton("View Profile");
		viewProfileButton.setBounds(110, 45, 100, 20);
		viewProfileButton.addActionListener(e -> actionListener.onActionPerformed("viewProfile", fetchedUsername));
		add(viewProfileButton);

		JButton upvoteButton = new JButton("Upvote");
		upvoteButton.setBounds(110, 65, 100, 20);
		upvoteButton.addActionListener(e -> actionListener.onActionPerformed("upvote", fetchedUsername));
		add(upvoteButton);

		JButton downvoteButton = new JButton("Downvote");
		downvoteButton.setBounds(110, 85, 100, 20);
		downvoteButton.addActionListener(e -> actionListener.onActionPerformed("downvote", fetchedUsername));
		add(downvoteButton);

		JButton nextButton = new JButton("Next");
		nextButton.setBounds(110, 105, 100, 20);
		nextButton.addActionListener(e -> actionListener.onActionPerformed("next"));
		add(nextButton);
	}
}