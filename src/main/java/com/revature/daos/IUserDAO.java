package com.revature.daos;
import java.util.ArrayList;

import com.revature.models.User;

public interface IUserDAO {
	public boolean createUser(User u, String p);
	public ArrayList<User> allCustomers();
	public User findUserByName(String u);
	public boolean userExists(String u);
	public boolean checkPass(String u, String p);
	public boolean updateUser(User u);
}
