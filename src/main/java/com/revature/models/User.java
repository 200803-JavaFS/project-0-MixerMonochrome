package com.revature.models;

import java.util.*;

public class User {
	private String username;
	private String userType;
	private ArrayList<Account> accounts;
	private Account activeAcct;
	
	public User(){
		super();
		username = "GuestUser";
		userType = "Guest";
		activeAcct = new Account();
		accounts = new ArrayList<Account>();
	}
	
	public User(String uname, String type) {
		super();
		username = uname;
		userType = type;
		activeAcct = new Account();
		//TEMP BELOW, pull actual connected IDs from Ownership table
		accounts = new ArrayList<Account>();
	}
	
	public String getName() {
		return username;
	}
	
	public void setName(String username) {
		this.username = username;
	}
	
	
	public String getType() {
		return userType;
	}
	
	public void setType(String type) {
		userType = type;
	}
	
	public ArrayList<Account> getAccts(){
		return accounts;
	}
	
	public void setAccts(ArrayList<Account> accounts){
		this.accounts = accounts;
		
	}
	
	public Account getActive() {
		return activeAcct;
	}
	
	public void setActive(Account changeTo) {
		activeAcct = changeTo;
	}
	
}
