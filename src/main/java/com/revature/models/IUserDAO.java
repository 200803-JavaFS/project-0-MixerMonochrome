package com.revature.models;
import java.util.ArrayList;

public interface IUserDAO {
	public boolean createUser(User u, String p);
	public ArrayList<User> allUsers();
	public User findUserByName(String u);
	public boolean userExists(String u);
	public boolean checkPass(String u, String p);
}
