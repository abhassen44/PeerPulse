package backend.models;

import java.util.Arrays;

public class UserImage
{
	private String username;
	private byte[] image;

	public UserImage(String username, byte[] image)
	{
		this.username = username;
		this.image = image;
	}

	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public byte[] getImage()
	{
		return image;
	}
	public void setImage(byte[] image)
	{
		this.image = image;
	}

	@Override
	public String toString()
	{
		return "UserImage{" +
				"username='" + username + '\'' +
				", image=" + Arrays.toString(image) +
				'}';
	}
}
