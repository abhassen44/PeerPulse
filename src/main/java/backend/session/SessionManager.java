package backend.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

public class SessionManager
{
	public static final long SESSION_DURATION_MS = 30 * 60 * 1000; // 30 minutes
	public static final ConcurrentHashMap<String, Long> sessions = new ConcurrentHashMap<>();

	public static boolean isLoggedIn(String username)
	{
		Long loginTime = sessions.get(username);
		if (loginTime == null)
			return false;

		long currentTime = System.currentTimeMillis();

		if ((currentTime - loginTime) < SESSION_DURATION_MS)
		{
			// Auto-renew session
			sessions.put(username, currentTime);
			return true;
		}
		else
		{
			// Session expired
			sessions.remove(username);
			return false;
		}
	}

	public static void login(String username)
	{
		sessions.put(username, System.currentTimeMillis());
	}

	public static void logout(String username)
	{
		sessions.remove(username);
	}

	public static void logoutAll()
	{
		sessions.clear();
	}
}
