package com.revature.models;

public class UserDAO {

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
}
