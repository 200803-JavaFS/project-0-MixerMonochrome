package com.revature.util;

import java.sql.*;
public class ConnectionManager {
	public static Connection getConnection() throws SQLException {
		
		//For compatibility with other technologies/frameworks will need to register our Driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:postgresql://databases.c2ga2loul9ng.us-east-2.rds.amazonaws.com:5432/Proj0";
		String username = "imhere";
		String password = "oneforall"; 
		
		return DriverManager.getConnection(url, username, password);
	}
}
