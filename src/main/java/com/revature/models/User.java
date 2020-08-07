package com.revature.models;

import java.util.*;

public class User {
	private String username;
	private String userType;
	private ArrayList<String> accountIDs;
	private String activeAcct;
	
	public User(){
		super();
		username = "GuestUser";
		userType = "Guest";
	}
	
	public User(String uname, String type) {
		super();
		username = uname;
		userType = type;
	}
	
	public String getName() {
		return username;
	}
	
	
	public String getType() {
		return userType;
	}
	
	public ArrayList<String> getAccts(){
		return accountIDs;
	}
	
	public String getActive() {
		return activeAcct;
	}
	
	public String setActive(String changeTo) {
		if(!accountIDs.contains(changeTo)) {
			
		}
	}
	
	public int getBalance(String acctID) {
		int bal = 0;
		//Grab bal from acctID in DB
		return bal;
	}
	
	public boolean alterBalance(char action, int amount, String acctID) {
		switch(action) {
		case 'W':
			if (amount < 0) {
				System.out.println("Can't withdraw a negative amount");
				return false;
			}
			else if (amount > getBalance(acctID)) {
				System.out.println("Can't withdraw more money than exists in account");
				return false;
			}
			//Connect to SQL acct table, add amount to acctID
			break;
		case 'D':
			if (amount < 0) {
				System.out.println("Can't deposit a negative amount");
			}
			//Connect to SQL acct table, subtract amount from acctID
			break;
		default:
			return false;
		}
			
		return true;
	}
	
	//Applies for a new account, will start closed
	public int applyForNew(){
		//Inserts a new row into Account table where available
		//Initializes with zero balance
		return 0;
	}
	
	//Applies for another user to have access to an existing account, 
	public boolean applyToShare(String username, String acctID) {
		//Adds row to Ownership table with userID to acctID.
		//if acctID type is Single, change to joint
		return true;
	}
	
}
