package com.revature.daos;

import java.util.ArrayList;

import com.revature.models.Account;

public interface IAccountDAO {
	public ArrayList<Account> getAccountsByUser(String u);
	public ArrayList<Account> allAccounts();
	public Account getAccountByID(int id);
	public boolean addAccount(Account a);
	//public boolean deleteAccount(Account a);
	public boolean updateAccount(Account a);
	public boolean addUserAccess(Account a, String u);
	public boolean removeUserAccess(Account a, String u);
	public boolean accountExists(int id);
	public ArrayList<Account> getAccountsByStatus(String s);
}
