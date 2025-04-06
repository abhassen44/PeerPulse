package backend.models;

import java.util.Date;

public class User
{
	private String username;
	private String name;
	private Date dateOfBirth;
	private String university;
	private int likes;
	private char sex;
	private Date dateJoined;

	// Constructor
	public User(String username, String name, Date dateOfBirth, String university, int likes, char sex, Date dateJoined)
	{
		this.username = username;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.university = university;
		this.likes = likes;
		this.sex = sex;
		this.dateJoined = dateJoined;
	}

	// Getters and Setters
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Date getDateOfBirth() { return dateOfBirth; }
	public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

	public String getUniversity() { return university; }
	public void setUniversity(String university) { this.university = university; }

	public int getLikes() { return likes; }
	public void setLikes(int likes) { this.likes = likes; }

	public char getSex() { return sex; }
	public void setSex(char sex) { this.sex = sex; }

	public Date getDateJoined() { return dateJoined; }
	public void setDateJoined(Date dateJoined) { this.dateJoined = dateJoined; }

	@Override
	public String toString()
	{
		return "User" +
					   "{" +
					   "username='" + username + '\'' +
					   ", name='" + name + '\'' +
					   ", university='" + university + '\'' +
					   ", likes=" + likes +
					   ", sex=" + sex +
					   ", dateJoined=" + dateJoined +
					   '}';
	}
}
