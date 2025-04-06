package backend.models;

import java.sql.Date;

public class Log
{
	private String username;
	private String action;
	private Date  date;

	public Log(String username, String action, Date date)
	{
		this.username = username;
		this.action = action;
		this.date = date;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}

	@Override
	public String toString()
	{
		return "Log" +
					   "{" +
					   "username='" + username + '\'' +
					   ", action='" + action + '\'' +
					   ", date=" + date +
					   '}';
	}
}
