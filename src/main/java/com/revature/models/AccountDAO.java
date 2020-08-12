package com.revature.models;

import java.util.ArrayList;

public class AccountDAO {
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

	public ArrayList<Account> findUserAccounts() {
		return new ArrayList<Account>();
	}
	
	public Account accountByID(String id) {
		//Take string and find/pull it from DB
		return new Account();
	}
}
