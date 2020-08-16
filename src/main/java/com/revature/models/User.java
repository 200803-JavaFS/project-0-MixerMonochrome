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
	private ArrayList<Integer> accounts;
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
		accounts = new ArrayList<Integer>();
	}
	
	public User(String username, String userType, String firstName, String lastName, String phoneNumber, 
			String address, int numAccounts, ArrayList<Integer> accounts) {
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
		this.accounts = new ArrayList<Integer>();
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
	
	public ArrayList<Integer> getAccts(){
		return accounts;
	}
	
	public void setAccts(ArrayList<Integer> accounts){
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((activeAcct == null) ? 0 : activeAcct.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + numAccounts;
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (numAccounts != other.numAccounts)
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String s = "username=" + username + ", userType=";
		if(userType.equals("C")) {
			s += "Customer";
		}
		else if(userType.equals("E")) {
			s += "Employee";
		}
		else if(userType.equals("A")) {
			s+= "Admin";
		}
		else {
			s+= "ERROR";
		}
		
		s+= ", firstName=" + firstName + ", lastName="
				+ lastName + ", phoneNumber=" + phoneNumber + ", address=" + address + ", numAccounts=" + numAccounts
				+ ", accounts=" + accounts;
		return  s;
	}
	
	
	
}
