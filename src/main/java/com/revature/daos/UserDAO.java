package com.revature.daos;

import java.sql.*;
import java.util.*;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionManager;

public class UserDAO implements IUserDAO {

	private static UserDAO userDAO;
	private static AccountDAO acctDAO;

	private UserDAO() {
		super();
	}

	public static UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new UserDAO();
			acctDAO = AccountDAO.getAcctDAO();
		}
		return userDAO;
	}

	@Override
	public boolean createUser(User u, String p) {
		try (Connection conn = ConnectionManager.getConnection()) {
			String sql = "insert into Users (username, psswrd, first_name, last_name, \r\n"
					+ "acct_type, phone_num, address, num_accts)\r\n" + "values\r\n" + "(?,?,?,?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, u.getName());
			statement.setString(2, p);
			statement.setString(3, u.getFirstName());
			statement.setString(4, u.getLastName());
			statement.setString(5, u.getType());
			statement.setString(6, u.getPhoneNumber());
			statement.setString(7, u.getAddress());
			statement.setInt(8, u.getNumAccts());
			statement.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<User> allCustomers() {
		try (Connection conn = ConnectionManager.getConnection()) {
			String sql = "select * from Users where acct_type = 'C';";
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			ArrayList<User> users = new ArrayList<User>();
			while (results.next()) {
				ArrayList<Integer> accountIDs = new ArrayList<Integer>();
				ArrayList<Account> accounts = acctDAO.getAccountsByUser(results.getString("username"));
				for (Account a : accounts) {
					accountIDs.add(a.getID());
				}
				users.add(new User(results.getString("username"), results.getString("acct_type"),
						results.getString("first_name"), results.getString("last_name"), results.getString("phone_num"),
						results.getString("address"), results.getInt("num_accts"), accountIDs));
			}
			return users;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User findUserByName(String u) {
		try (Connection conn = ConnectionManager.getConnection()) {
			String sql = "select * from Users where username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, u);
			ResultSet user = statement.executeQuery();
			while (user.next()) {
				ArrayList<Integer> accountIDs = new ArrayList<Integer>();
				ArrayList<Account> accounts = acctDAO.getAccountsByUser(user.getString("username"));
				for (Account a : accounts) {
					accountIDs.add(a.getID());
				}
				return new User(user.getString("username"), user.getString("acct_type"), user.getString("first_name"),
						user.getString("last_name"), user.getString("phone_num"), user.getString("address"),
						user.getInt("num_accts"), accountIDs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean userExists(String u) {
		try (Connection conn = ConnectionManager.getConnection()) {
			String sql = "select * from Users where username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, u);
			ResultSet user = statement.executeQuery();
			if (user.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkPass(String u, String pass) {
		try (Connection conn = ConnectionManager.getConnection()) {
			String sql = "select * from Users where username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, u);
			ResultSet user = statement.executeQuery();
			user.next();
			if (user.getString("psswrd").equals(pass)) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean updateUser(User u) {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "update Users set phone_num = ?, address = ?, num_accts=? where username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, u.getPhoneNumber());
			statement.setString(2,  u.getAddress());
			statement.setInt(3, u.getNumAccts());
			statement.setString(4,u.getName());
			statement.execute();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
