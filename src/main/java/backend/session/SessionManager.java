package backend.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;
import java.util.UUID;

public class SessionManager
{
	public static final long SESSION_DURATION_MS = 30 * 60 * 1000; // 30 minutes
	// Map to store tokens and their expiration times
	private static final ConcurrentHashMap<String, SessionData> sessions = new ConcurrentHashMap<>();

	// Class to hold session data
	private static class SessionData {
		String username;
		long expirationTime;

		SessionData(String username, long expirationTime) {
			this.username = username;
			this.expirationTime = expirationTime;
		}
	}

	// Check if a token is valid and auto-renew if valid
	public static boolean isAuthorized(String token)
	{
		SessionData sessionData = sessions.get(token);
		if (sessionData == null)
			return false;

		long currentTime = System.currentTimeMillis();

		if (currentTime < sessionData.expirationTime)
		{
			// Auto-renew session
			sessionData.expirationTime = currentTime + SESSION_DURATION_MS;
			return true;
		}
		else
		{
			// Session expired
			sessions.remove(token);
			return false;
		}
	}

	// Login and return a token
	public static String login(String username)
	{
		String token = UUID.randomUUID().toString(); // Generate a unique token
		long expirationTime = System.currentTimeMillis() + SESSION_DURATION_MS;
		sessions.put(token, new SessionData(username, expirationTime));
		return token;
	}

	// Logout using token
	public static void logout(String token)
	{
		sessions.remove(token);
	}

	// Logout all sessions for a specific username
	public static void logoutAllForUser(String username)
	{
		sessions.entrySet().removeIf(entry -> entry.getValue().username.equals(username));
	}

	// Logout all sessions
	public static void logoutAll()
	{
		sessions.clear();
	}
}
