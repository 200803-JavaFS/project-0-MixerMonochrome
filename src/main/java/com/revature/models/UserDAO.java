package com.revature.models;

import java.util.*;

public class UserDAO implements IUserDAO{

	private static UserDAO userDAO;
	
	private UserDAO() {
		super();
	}
	
	public static UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new UserDAO();
		}
		return userDAO;
	}

	@Override
	public boolean createUser(User u, String p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<User> allUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByName(String u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean userExists(String u) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean checkPass(String u, String pass) {
		return false;
	}
}
