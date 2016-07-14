package se.leanbit.ticketsystem.model;

public class LoginInfo
{
	private String userName;
	private String password;
	
	public void LoginInfo(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getUserName()
	{
		return userName;
	}
}
