package backend.services;

import backend.DAO.UniversityDAO;
import backend.models.University;

public class UniversityService
{
	// This class will handle university-related operations
	// such as adding new universities, updating university information, etc.

	private final UniversityDAO universityDAO = new UniversityDAO();

	// Example method to add a new university
	public String addUniversity(String universityName, String location)
	{
		if (universityDAO.findByName(universityName) != null)
		{
			return "ERROR|University already exists";
		}

		University uni = new University(universityName, location, 0, null);
		universityDAO.insert(uni);
		return "SUCCESS|University added successfully";
	}

	// Example method to update university information
	public String addAdminUser(String universityName, String username)
	{
		University uni = universityDAO.findByName(universityName);
		if (uni == null)
		{
			return "ERROR|University not found";
		}
		uni.setAdminUsername(username);
		universityDAO.update(uni);
		return "SUCCESS|University information updated successfully";
	}

	public String deleteUniversity(String universityName)
	{
		if (universityDAO.findByName(universityName) == null)
		{
			return "ERROR|University not found";
		}

		universityDAO.delete(universityName);
		return "SUCCESS|University deleted successfully";
	}

	public String getUniversity(String universityName)
	{
		University uni = universityDAO.findByName(universityName);
		if (uni == null)
		{
			return "ERROR|University not found";
		}
		return "SUCCESS|" + uni.toString();
	}
}
