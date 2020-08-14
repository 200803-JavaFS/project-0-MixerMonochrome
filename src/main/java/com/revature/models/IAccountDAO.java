package com.revature.models;

import java.util.ArrayList;

public interface IAccountDAO {
	public ArrayList<Account> getAccountsByUser(String u);
	public ArrayList<Account> allAccounts();
	public Account getAccountByID(String id);
	public boolean addAccount(Account a);
	//public boolean deleteAccount(Account a);
	public boolean updateAccount(Account a);
	public boolean addUserAccess(Account a, String u);
	public boolean removeUserAccess(Account a, String u);
	public boolean accountExists(String id);
}
