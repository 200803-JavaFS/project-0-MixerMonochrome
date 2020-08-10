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
		activeAcct = "";
		accountIDs = new ArrayList<String>();
	}
	
	public User(String uname, String type) {
		super();
		username = uname;
		userType = type;
		activeAcct = "";
		//TEMP BELOW, pull actual connected IDs from Ownership table
		accountIDs = new ArrayList<String>();
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
			activeAcct = changeTo;
			return changeTo;
		}
		else{
			System.out.println("You do not have access to the account you entered.");
			return "";
		}
	}
	
	public int getBalance() {
		int bal = 0;
		//Grab bal from activeAcct in DB
		return bal;
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
