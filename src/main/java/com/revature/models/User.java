package com.revature.models;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String userType;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private int numAccounts;
	private ArrayList<String> accounts;
	private Account activeAcct;
	
	public User(){
		super();
		username = "GuestUser";
		userType = "Guest";
		firstName = "John";
		lastName = "Doe";
		phoneNumber = "XXX-XXX-XXXX";
		address = "Pleasant Lane, Mirage Island";
		numAccounts = 0;
		activeAcct = null;
		accounts = null;
	}
	
	public User(String username, String userType, String firstName, String lastName, String phoneNumber, 
			String address, int numAccounts, ArrayList<String> accounts) {
		super();
		this.username = username;
		this.userType = userType;
		this.activeAcct = null;
		this.accounts = accounts;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.numAccounts = numAccounts;
	}
	
	public User(String username, String userType, String firstName, String lastName, String phoneNumber,
			String address) {
		super();
		this.username = username;
		this.userType = userType;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.numAccounts = 0;
		this.accounts = null;
		this.activeAcct = null;
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
	
	public ArrayList<String> getAccts(){
		return accounts;
	}
	
	public void setAccts(ArrayList<String> accounts){
		this.accounts = accounts;
		
	}
	
	public Account getActive() {
		return activeAcct;
	}
	
	public void setActive(Account changeTo) {
		activeAcct = changeTo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getNumAccts() {
		return numAccounts;
	}
	
	public void setNumAccts(int numAccounts) {
		this.numAccounts = numAccounts;
	}
	
}
