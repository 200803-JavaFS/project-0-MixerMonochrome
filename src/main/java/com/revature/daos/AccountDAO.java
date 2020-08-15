package com.revature.daos;

import java.util.ArrayList;

import com.revature.models.Account;
import com.revature.util.ConnectionManager;

import java.sql.*;

public class AccountDAO implements IAccountDAO {
	private static AccountDAO acctDAO;

	private AccountDAO() {
		super();
	}

	public static AccountDAO getAcctDAO() {
		if (acctDAO == null) {
			acctDAO = new AccountDAO();
		}
		return acctDAO;
	}

	@Override
	public ArrayList<Account> getAccountsByUser(String u) {
		try (Connection conn = ConnectionManager.getConnection()) {
			String sql = "SELECT acct_ID from Ownership WHERE username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, u);
			ResultSet actIDs = statement.executeQuery();
			ArrayList<Account> accountList = new ArrayList<Account>();
			while(actIDs.next()) {	
			accountList.add(getAccountByID(actIDs.getInt("acct_ID")));
			}
			return accountList;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Account> allAccounts() {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "select * from Accounts;";
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			ArrayList<Account> accounts = new ArrayList<Account>();
			while(results.next()) {
				accounts.add(new Account(results.getInt("acct_id"),results.getString("acct_type"),results.getInt("balance"),results.getString("status")));
			}
			return accounts;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account getAccountByID(int id) {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "select * from Accounts where acct_id = " + id + ";";
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			results.next();
			return new Account(results.getInt("acct_id"),results.getString("acct_type"),results.getInt("balance"),results.getString("status"));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addAccount(Account a) {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "insert into Accounts (acct_type,balance,status) values ('" + a.getType() + "'," + a.getBalance() + ",'" + a.getStatus() + "') returning acct_id;";
			Statement statement = conn.createStatement();
			statement.execute(sql);
			ResultSet result = statement.getResultSet(); //IF DOESN'T WORK, CHANGE TO .EXECUTE AND .GETRESULTSET
			result.next();
			a.setID(result.getInt("acct_id"));
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateAccount(Account a) {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "update Accounts set acct_type = '" + a.getType() +
					"', balance = " + a.getBalance() + ", status = '" + a.getStatus() +
					"' where acct_id = " + a.getID() + ";";
			Statement statement = conn.createStatement();
			statement.execute(sql);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addUserAccess(Account a, String u) {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "insert into Ownership(acct_id,username) values (" + a.getID() + ",'" + u + "');";
			Statement statement = conn.createStatement();
			statement.execute(sql);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeUserAccess(Account a, String u) {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "delete from Ownership where acct_id = " + a.getID() +
					", username = '" + u + "';";
			Statement statement = conn.createStatement();
			statement.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean accountExists(int id) {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "select * from Account where acct_id = " + id + ";";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if(result.next()) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<Account> getAccountsByStatus(String s) {
		try(Connection conn = ConnectionManager.getConnection()){
			String sql = "select * from Account where status = '" + s + "';";
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			ArrayList<Account> accounts = new ArrayList<Account>();
			while(results.next()) {
				accounts.add(new Account(results.getInt("acct_id"),results.getString("acct_type"),results.getInt("balance"),results.getString("status")));
			}
			return accounts;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
