package backend.models;

import java.util.Date;

public class Transactions
{
	private String sender;
	private String receiver;
	private int amount;
	private Date date;

	public Transactions(String sender, String receiver, int amount, Date date)
	{
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.date = date;
	}
	public String getSender()
	{
		return sender;
	}
	public void setSender(String sender)
	{
		this.sender = sender;
	}
	public String getReceiver()
	{
		return receiver;
	}
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}
	public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
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
		return "Transactions [sender=" + sender + ", receiver=" + receiver + ", amount=" + amount + ", date=" + date
				+ "]";
	}
}
