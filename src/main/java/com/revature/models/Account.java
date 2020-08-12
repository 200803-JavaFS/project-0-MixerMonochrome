package com.revature.models;

import java.util.*;

public class Account {
	private String id;
	private String type;
	private int balance;
	private String status;
	private ArrayList<String> owners;
	
	public Account() {
		super();
	}
	
	public Account(String id, String type, int balance, String status, ArrayList<String> owners) {
		this.id = id;
		this.type = type;
		this.balance = balance;
		this.status = status;
		this.owners = owners; 
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public ArrayList<String> getOwners(){
		return owners;
	}
	
	public void setOwners(ArrayList<String> owners) {
		this.owners = owners;
	}
}
