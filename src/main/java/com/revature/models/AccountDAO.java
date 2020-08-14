package com.revature.models;

import java.util.ArrayList;
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
			accountList.add(getAccountByID(actIDs.getString("acct_ID")));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAccount(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAccount(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addUserAccess(Account a, String u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUserAccess(Account a, String u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accountExists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
}
