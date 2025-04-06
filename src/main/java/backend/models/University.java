package backend.models;

public class University
{
	private String name;
	private String location;
	private int students;
	private String adminUsername;

	public University(String name, String location, int students, String adminUsername)
	{
		this.name = name;
		this.location = location;
		this.students = students;
		this.adminUsername = adminUsername;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getLocation()
	{
		return location;
	}
	public int getStudents()
	{
		return students;
	}
	public void setStudents(int students)
	{
		this.students = students;
	}
	public String getAdminUsername()
	{
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername)
	{
		this.adminUsername = adminUsername;
	}

	@Override
	public String toString()
	{
		return "University{" +
				"name='" + name + '\'' +
				", location='" + location + '\'' +
				", students=" + students +
				", adminUsername='" + adminUsername + '\'' +
				'}';
	}
}
