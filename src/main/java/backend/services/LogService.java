package backend.services;

import backend.DAO.LogDAO;
import backend.models.Log;

import java.util.List;

public class LogService
{
	LogDAO logDAO = new LogDAO();
	public String getLogs(String username)
	{
		List<Log> list = logDAO.findByUsername(username);

		if (list == null || list.isEmpty())
		{
			return "ERROR|No logs found for user: " + username;
		}
		StringBuilder res = new StringBuilder("SUCCESS|");
		for (Log log : list)
		{
			res.append(log.getAction()).append("~").append(log.getDate()).append("|");
		}
		return res.toString();
	}
}
